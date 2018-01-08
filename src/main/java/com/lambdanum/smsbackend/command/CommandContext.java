package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.messaging.Message;
import com.lambdanum.smsbackend.messaging.MessageProvider;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandContext {

    private ArrayList<Object> args = new ArrayList<>();

    private Message message;
    private User user;
    private MessageProvider messageProvider;

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
}
