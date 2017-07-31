package com.lambdanum.smsbackend.command.tree;


import com.lambdanum.smsbackend.command.CommandConstruct;

import java.util.HashMap;
import java.util.Map;

public class DecisionNode {

    private Map<String, DecisionNode> children = new HashMap<>();

    private CommandConstruct commandConstruct;

    public DecisionNode() {}

    public DecisionNode(CommandConstruct commandConstruct) {
        this.commandConstruct = commandConstruct;
    }

    public void registerChild(String command, DecisionNode child) {
        children.put(command,child);
    }

    public boolean hasChild(String command) {
        return children.containsKey(command);
    }

    public DecisionNode getChild(String command) {
        return children.get(command);
    }

    public boolean isExitNode() {
        return commandConstruct != null;
    }

    public void setCommandConstruct(CommandConstruct commandConstruct) {
        this.commandConstruct = commandConstruct;
    }

    public Object invoke(Object... args) {
        return commandConstruct.invoke(args);
    }

    public static DecisionNode createRootNode() {
        return new DecisionNode();
    }
}
