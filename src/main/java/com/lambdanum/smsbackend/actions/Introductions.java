package com.lambdanum.smsbackend.actions;

import com.lambdanum.smsbackend.command.*;
import com.lambdanum.smsbackend.database.UserDAO;
import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.identity.UserRoleEnum;
import com.lambdanum.smsbackend.nlp.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CommandListener
public class Introductions {

    private UserDAO userDAO;

    @Autowired
    public Introductions(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @CommandHandler(value = "hello", requiredRole = UserRoleEnum.NOBODY)
    public void registerUser(CommandContext commandContext) {
        commandContext.reply("Hello. You can start by telling me your name.");
    }

    @Conversational("registerUser")
    @CommandHandler(value = "name is <str> ...", requiredRole = UserRoleEnum.NOBODY)
    public void setUserName(CommandContext context, List<String> name) {
        registerUserWithName(context,name);
    }

    @CommandHandler(value = "hello name is <str> ...", requiredRole = UserRoleEnum.NOBODY)
    public void registerUserWithName(CommandContext commandContext, List<String> name) {
        User user = commandContext.getUser();
        String userName = StringHelper.stripPunctuation(StringUtils.join(name, " "));
        user.setName(userName);
        user.addRole(UserRoleEnum.INTRODUCED);
        userDAO.persist(user);
        commandContext.reply(String.format("Hello %s." , userName));

        try {
            if (name.get(0).equals("Harry") && name.get(1).equals("Potter")) {
                commandContext.reply("My name is Tom Riddle.");
            }
        } catch (Exception e) { }
    }

    @CommandHandler(value = "secret", requiredRole = UserRoleEnum.INTRODUCED)
    public void secretCommand(CommandContext context){
        context.reply("This is an example of a restricted command.");
    }

    @CommandHandler("thank")
    public String thanks() {
        return "You're welcome!";
    }
}
