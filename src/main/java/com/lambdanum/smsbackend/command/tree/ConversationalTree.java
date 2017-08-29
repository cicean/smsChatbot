package com.lambdanum.smsbackend.command.tree;

import java.util.HashMap;
import java.util.Map;

public class ConversationalTree {

    private Map<String, DecisionNode> methodTrees = new HashMap<>();

    public void addConversationalMethodTree(String methodName, DecisionNode node) {
        methodTrees.put(methodName,node);
    }

    public DecisionNode getConversationalMethodTree(String methodName) {
        return methodTrees.get(methodName);
    }

    public boolean containsConversationalMethod(String methodName) {
        return methodTrees.containsKey(methodName);
    }

    public DecisionNode getOrCreateRootNodeForMethod(String methodName) {
        if (methodTrees.containsKey(methodName)) {
            return methodTrees.get(methodName);
        }
        DecisionNode newRootNode = new DecisionNode();
        methodTrees.put(methodName,newRootNode);
        return newRootNode;
    }
}
