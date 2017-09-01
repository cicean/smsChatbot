package com.lambdanum.smsbackend.messaging;

import com.lambdanum.smsbackend.actions.exceptions.UnimplementedCommandException;
import com.lambdanum.smsbackend.command.CommandContext;
import com.lambdanum.smsbackend.command.CommandContextFactory;
import com.lambdanum.smsbackend.command.CommandDispatcher;
import com.lambdanum.smsbackend.command.UnknownCommandException;
import com.lambdanum.smsbackend.command.tree.UnauthorizedCommandException;
import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.identity.UserRoleEnum;
import com.lambdanum.smsbackend.identity.UserService;
import com.lambdanum.smsbackend.nlp.TokenizerService;
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
    private TokenizerService tokenizerService;

    @Autowired
    public MessageReplyService(CommandDispatcher commandDispatcher, UserService userService,
                               MessageProviderCollection messageProviderCollection, CommandContextFactory commandContextFactory,
                               TokenizerService tokenizerService) {
        this.commandDispatcher = commandDispatcher;
        this.userService = userService;
        this.messageProviderCollection = messageProviderCollection;
        this.commandContextFactory = commandContextFactory;
        this.tokenizerService = tokenizerService;
    }

    public void replyToMessage(Message incomingMessage) {
        User user = userService.getOrCreateUser(incomingMessage.getSource(),incomingMessage.getMessageProvider());
        MessageProvider provider = messageProviderCollection.getMessageProvider(incomingMessage.getMessageProvider());
        CommandContext context = commandContextFactory.getCommandContext(incomingMessage, user, provider);
        try {
            Object result = commandDispatcher.executeCommand(incomingMessage.getContent(), context);
            if (result != null) {
                context.reply(result.toString());
            }
        } catch (UnknownCommandException e) {
            logger.warn("Unknown command : " + incomingMessage.getContent());
            logger.warn("tokenized command : " + tokenizerService.tokenizeAndStem(incomingMessage.getContent()));
        } catch (UnauthorizedCommandException e) {
            logger.warn("Unauthorized command call : " + incomingMessage.getContent());
        } catch (UnimplementedCommandException e) {
            logger.warn("NotImplemented command : " + incomingMessage.getContent() + String.format(" [method='%s']",e.getMessage()));
            if (user.hasRole(UserRoleEnum.INTRODUCED)) {
                context.reply("Opps! Looks like this has not been implemented yet! Sorry about that!");
            }
        }
    }
}
