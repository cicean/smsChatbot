package com.lambdanum.smsbackend.messaging;

import com.lambdanum.smsbackend.command.CommandContext;
import com.lambdanum.smsbackend.command.CommandContextFactory;
import com.lambdanum.smsbackend.command.CommandDispatcher;
import com.lambdanum.smsbackend.command.UnknownCommandException;
import com.lambdanum.smsbackend.command.tree.UnauthorizedCommandException;
import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.identity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageReplyService {

    private static final Logger logger = LoggerFactory.getLogger(MessageReplyService.class);

    private CommandDispatcher commandDispatcher;
    private UserService userService;
    private MessageProviderCollection messageProviderCollection;
    private CommandContextFactory commandContextFactory;

    @Autowired
    public MessageReplyService(CommandDispatcher commandDispatcher, UserService userService,
                               MessageProviderCollection messageProviderCollection, CommandContextFactory commandContextFactory) {
        this.commandDispatcher = commandDispatcher;
        this.userService = userService;
        this.messageProviderCollection = messageProviderCollection;
        this.commandContextFactory = commandContextFactory;
    }

    public void replyToMessage(Message incomingMessage) {
        User user = userService.getOrCreateUser(incomingMessage.getSource(),incomingMessage.getMessageProvider());
        MessageProvider provider = messageProviderCollection.getMessageProvider(incomingMessage.getMessageProvider());
        try {
            CommandContext context = commandContextFactory.getCommandContext(incomingMessage, user, provider);
            commandDispatcher.executeCommand(incomingMessage.getContent(), context);
        } catch (UnknownCommandException e) {
            logger.warn("Unknown command : " + incomingMessage.getContent());
        } catch (UnauthorizedCommandException e) {
            logger.warn("Unauthorized command call : " + incomingMessage.getContent());
        }
    }
}
