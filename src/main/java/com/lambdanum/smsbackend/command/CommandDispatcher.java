package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.command.tree.DecisionNode;
import com.lambdanum.smsbackend.command.tree.ReservedTokenConverter;
import com.lambdanum.smsbackend.nlp.TokenizerService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class CommandDispatcher {

    private static Logger logger = LoggerFactory.getLogger(CommandDispatcher.class);

    private DecisionNode rootNode = DecisionNode.createRootNode();
    private HashMap<String, ReservedTokenConverter> tokenConverters = new HashMap<>();
    private TokenizerService tokenizerService;

    @Autowired
    public CommandDispatcher(ApplicationContext applicationContext, List<ReservedTokenConverter> tokenConverters, TokenizerService tokenizerService) {
        this.tokenizerService = tokenizerService;

        for (Object listener : applicationContext.getBeansWithAnnotation(CommandListener.class).values()) {
            List<Method> methods = Arrays.asList(listener.getClass().getMethods());
            for (Method method : methods) {
                if (method.getAnnotation(CommandHandler.class) == null) {
                    continue;
                }

                DecisionNode subTree = rootNode;

                for (String word : method.getAnnotation(CommandHandler.class).value().split(" ")) {
                    if (!subTree.hasChild(word)) {
                        subTree.registerChild(word,new DecisionNode());
                    }
                    subTree = subTree.getChild(word);
                }

                MethodInvocationWrapper action = new MethodInvocationWrapper(listener, method);
                if (subTree.isExitNode()) {
                    logger.error("Error initializing command tree. Multiple identical command definitions.");
                    throw new IllegalStateException("Command '" + method.getAnnotation(CommandHandler.class).value() + "' defined multiple times.");
                }
                subTree.setMethodInvocationWrapper(action);

            }

        }
        logger.info("Initialized command tree.");

        tokenConverters.stream().forEach(converter -> this.tokenConverters.put(converter.getMatchedToken(),converter));

        logger.info("Initialized Token converters.");
    }

    public Object executeCommand(String command) {
        List<String> tokens = tokenizerService.tokenizeAndStem(command);
        String[] tokenArray = tokens.toArray(new String[tokens.size()]);
        String[] referenceString = command.split(" ");
        if (tokens.size() != referenceString.length) {
            logger.warn("Stemmed tokens count different to referenceString word count. Using tokenized version only. Parameters might be altered.");
            return explore(rootNode, tokenArray, tokenArray, new CommandContext());
        }
        return explore(rootNode, tokenArray, referenceString, new CommandContext());
    }

    private Object explore(DecisionNode subtree, String[] command, String[] reference, CommandContext context) {
        if (command.length == 0) {
            if (!subtree.isExitNode()) {
                throw new UnknownCommandException();
            }
            return subtree.invoke(context);
        }

        String nextWord = command[0];

        if (subtree.hasChild(nextWord)) {
            return explore(subtree.getChild(nextWord), ArrayUtils.subarray(command, 1, command.length), ArrayUtils.subarray(reference, 1, reference.length), context);
        }

        for (String child : subtree.getChildren()) {
            if (tokenConverters.containsKey(child)) {
                if (tokenConverters.get(child).isMatching(nextWord)) {
                    if(isStartOfArrayParameter(subtree.getChild(child))) {
                        List<Object> arrayParameter = getArrayParameterStartingHere(tokenConverters.get(child),subtree,reference);
                        context.addArgument(arrayParameter);
                        return explore(subtree.getChild(child).getChild("..."), ArrayUtils.subarray(command, arrayParameter.size(), command.length), ArrayUtils.subarray(reference, arrayParameter.size(), reference.length), context);

                    }
                    context.addArgument(tokenConverters.get(child).getMatchingObject(nextWord));
                    return explore(subtree.getChild(child), ArrayUtils.subarray(command, 1, command.length),ArrayUtils.subarray(reference, 1, reference.length), context);
                }
            }
        }

        return explore(subtree, ArrayUtils.subarray(command, 1, command.length), ArrayUtils.subarray(reference, 1, reference.length), context);
    }

    private List<Object> getArrayParameterStartingHere(ReservedTokenConverter converter, DecisionNode node, String[] command) {
        if (command.length == 0) {
            return Collections.emptyList();
        }
        List<Object> arrayParameter = new ArrayList<>();

        Set<String> exitWords = node.getChild(converter.getMatchedToken()).getChild("...").getChildren();
        Set<String> exitTokens = node.getChild(converter.getMatchedToken()).getChild("...").getReservedChildren();

        for (String word : command) {
            if (exitWords.contains(word) || !converter.isMatching(word) || setContainsMatcherToken(exitTokens, word)) {
                break;
            }
            arrayParameter.add(converter.getMatchingObject(word));
        }

        return arrayParameter;
    }

    private boolean setContainsMatcherToken(Set<String> tokens, String matchedWord) {
        return tokens.stream().anyMatch(token -> tokenConverters.get(token).isMatching(matchedWord));
    }

    private boolean isStartOfArrayParameter(DecisionNode node) {
        return node.hasChild("...");
    }
}
