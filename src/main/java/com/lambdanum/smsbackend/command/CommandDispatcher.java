package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.command.tree.DecisionNode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class CommandDispatcher {

    private LinkedHashMap<String, Method> commands;

    private DecisionNode rootNode = DecisionNode.createRootNode();

    @Autowired
    public CommandDispatcher(List<CommandListener> commandListeners) {


        for (CommandListener listener : commandListeners) {
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

                CommandConstruct action = new CommandConstruct(listener, method);
                subTree.setCommandConstruct(action);

            }

        }
        System.out.println("Done init tree");
        executeCommand("foobar");
    }

    public Object executeCommand(String command) {
        return explore(rootNode, command.split(" "));
    }

    private Object explore(DecisionNode subtree, String[] command) {
        if (command.length == 0) {
            return subtree.invoke();
        }

        String nextWord = command[0];

        if (subtree.hasChild(nextWord))  {
            return explore(subtree.getChild(nextWord), ArrayUtils.subarray(command, 1, command.length));
        } else {
            return "";
        }
    }
}
