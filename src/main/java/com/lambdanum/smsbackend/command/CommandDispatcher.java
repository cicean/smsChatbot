package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.command.tree.DecisionNode;
import com.lambdanum.smsbackend.command.tree.ReservedTokenConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class CommandDispatcher {

    private DecisionNode rootNode = DecisionNode.createRootNode();
    private HashMap<String, ReservedTokenConverter> tokenConverters = new HashMap<>();

    @Autowired
    public CommandDispatcher(ApplicationContext applicationContext, List<ReservedTokenConverter> tokenConverters) {

        for (Object listener : applicationContext.getBeansWithAnnotation(CommandListener.class).values()) {
            List<Method> methods = Arrays.asList(listener.getClass().getMethods());
            for (Method method : methods) {
                if (method.getAnnotation(CommandHandler.class) == null) {
                    continue;
                }

                DecisionNode subTree = rootNode;

                for (String word : method.getAnnotation(CommandHandler.class).command().split(" ")) {
                    if (!subTree.hasChild(word)) {
                        subTree.registerChild(word,new DecisionNode());
                    }
                    subTree = subTree.getChild(word);
                }

                MethodInvocationWrapper action = new MethodInvocationWrapper(listener, method);
                if (subTree.isExitNode()) {
                    throw new IllegalStateException("Command '" + method.getAnnotation(CommandHandler.class).command() + "' defined multiple times.");
                }
                subTree.setMethodInvocationWrapper(action);

            }

        }
        System.out.println("Done init tree");

        tokenConverters.stream().forEach(converter -> this.tokenConverters.put(converter.getMatchedToken(),converter));

        System.out.println("Done init tokenConverters");
        executeCommand("tell my name is foobar hello");
    }

    public Object executeCommand(String command) {
        return explore(rootNode, command.split(" "), new CommandContext());
    }

    private Object explore(DecisionNode subtree, String[] command, CommandContext context) {
        if (command.length == 0) {
            if (!subtree.isExitNode()) {
                throw new IncompleteCommandException();
            }
            return subtree.invoke(context.getArgs());
        }

        String nextWord = command[0];

        if (subtree.hasChild(nextWord)) {
            return explore(subtree.getChild(nextWord), ArrayUtils.subarray(command, 1, command.length), context);
        }

        for (String child : subtree.getChildren()) {
            if (tokenConverters.containsKey(child)) {
                if (tokenConverters.get(child).isMatching(nextWord)) {
                    if(isStartOfArrayParameter(subtree.getChild(child))) {
                        List<Object> arrayParameter = getArrayParameterStartingHere(tokenConverters.get(child),subtree,command);
                        context.addArgument(arrayParameter);
                        return explore(subtree.getChild(child).getChild("..."), ArrayUtils.subarray(command, arrayParameter.size(), command.length),context);

                    }
                    context.addArgument(tokenConverters.get(child).getMatchingObject(nextWord));
                    return explore(subtree.getChild(child), ArrayUtils.subarray(command, 1, command.length),context);
                }
            }
        }

        return explore(subtree, ArrayUtils.subarray(command, 1, command.length), context);
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
