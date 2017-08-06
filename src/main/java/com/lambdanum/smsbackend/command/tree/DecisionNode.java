package com.lambdanum.smsbackend.command.tree;


import com.lambdanum.smsbackend.command.CommandContext;
import com.lambdanum.smsbackend.command.MethodInvocationWrapper;
import com.lambdanum.smsbackend.command.UserRole;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DecisionNode {

    private Map<String, DecisionNode> children = new HashMap<>();

    private MethodInvocationWrapper methodInvocationWrapper;
    private UserRole requiredRole;

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

    public Object invoke(CommandContext context) {
        if (context.getUser().hasRole(requiredRole)) {
            return methodInvocationWrapper.invoke(context, context.getArgs());
        }
        throw new UnauthorizedCommandException();
    }

    public static DecisionNode createRootNode() {
        return new DecisionNode();
    }

    public Set<String> getChildren() {
        return children.keySet();
    }

    public Set<String> getReservedChildren() {
        return children.keySet().stream().filter(child -> child.startsWith("<") && child.endsWith(">")).collect(Collectors.toSet());
    }

    public UserRole getRequiredRole() {
        return requiredRole;
    }

    public void setRequiredRole(UserRole requiredRole) {
        this.requiredRole = requiredRole;
    }
}
