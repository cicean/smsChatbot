package com.lambdanum.smsbackend.messaging;

import com.lambdanum.smsbackend.command.CommandDispatcher;
import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.identity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageReplyService {

    private CommandDispatcher commandDispatcher;
    private UserService userService;
    private MessageProviderCollection messageProviderCollection;

    @Autowired
    public MessageReplyService(CommandDispatcher commandDispatcher, UserService userService, MessageProviderCollection messageProviderCollection) {
        this.commandDispatcher = commandDispatcher;
        this.userService = userService;
        this.messageProviderCollection = messageProviderCollection;
    }

    public void replyToMessage(Message incomingMessage) {
        User user = userService.getOrCreateUser(incomingMessage.getSource(),incomingMessage.getMessageProvider());
        MessageProvider provider = messageProviderCollection.getMessageProvider(incomingMessage.getMessageProvider());
        commandDispatcher.executeCommand(incomingMessage, user, provider);
    }
}
