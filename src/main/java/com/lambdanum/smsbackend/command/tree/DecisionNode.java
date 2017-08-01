package com.lambdanum.smsbackend.command.tree;


import com.lambdanum.smsbackend.command.MethodInvocationWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DecisionNode {

    private Map<String, DecisionNode> children = new HashMap<>();

    private MethodInvocationWrapper methodInvocationWrapper;

    public DecisionNode() {}

    public DecisionNode(MethodInvocationWrapper methodInvocationWrapper) {
        this.methodInvocationWrapper = methodInvocationWrapper;
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
        return methodInvocationWrapper != null;
    }

    public void setMethodInvocationWrapper(MethodInvocationWrapper methodInvocationWrapper) {
        this.methodInvocationWrapper = methodInvocationWrapper;
    }

    public Object invoke(Object... args) {
        return methodInvocationWrapper.invoke(args);
    }

    public static DecisionNode createRootNode() {
        return new DecisionNode();
    }

    public Set<String> getChildren() {
        return children.keySet();
    }
}
