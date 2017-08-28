package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.messaging.Message;
import com.lambdanum.smsbackend.messaging.MessageProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CommandContextFactory {

    private HashMap<Integer,CommandContext> commandContexts = new HashMap<>();

    public CommandContext getCommandContext(Message message, User user, MessageProvider messageProvider) {
        if (commandContexts.containsKey(user.getId())) {
            CommandContext context = commandContexts.get(user.getId());
            context.setMessage(message);
            context.cleanupConversationalCommands();
            context.clearArguments();
            return commandContexts.get(user.getId());
        }
        CommandContext context = new CommandContext(message,user,messageProvider);
        commandContexts.put(user.getId(),context);
        return context;
    }
    //TODO add context cache cleanup
}
