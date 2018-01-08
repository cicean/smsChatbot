package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.command.tree.DecisionNode;
import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.messaging.Message;
import com.lambdanum.smsbackend.messaging.MessageProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandContext {

    private ArrayList<Object> args = new ArrayList<>();

    private Message message;
    private User user;
    private MessageProvider messageProvider;

    private List<ConversationalCommand> conversationalCommands = new ArrayList<>();

    public CommandContext() {}

    public CommandContext(Message message, User user, MessageProvider messageProvider) {
        this.message = message;
        this.user = user;
        this.messageProvider = messageProvider;
    }

    public void addArgument(Object...args) {
        this.args.addAll(Arrays.asList(args));
    }

    public Object[] getArgs() {
        return args.toArray();
    }

    public void reply(String messageContent) {
        Message reply = message.createResponseMessage(message.getSource(), messageContent);

        messageProvider.sendMessage(reply);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ConversationalCommand> getConversationalCommands() {
        return this.conversationalCommands;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void addConversationalCommand(DecisionNode conversationalRootNode) {
        conversationalCommands.add(new ConversationalCommand(conversationalRootNode));
    }

    public void clearArguments() {
        args = new ArrayList<>();
    }

    public void cleanupConversationalCommands() {
        boolean hasRemovedOne = true;
        while (hasRemovedOne) {
            hasRemovedOne = false;
            for (ConversationalCommand conversationalCommand : conversationalCommands) {
                if (conversationalCommand.isExpired()) {
                    conversationalCommands.remove(conversationalCommand);
                    hasRemovedOne = true;
                    break;
                }
            }
        }
    }
}
