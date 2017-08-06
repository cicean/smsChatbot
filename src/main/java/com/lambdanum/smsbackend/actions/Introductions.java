package com.lambdanum.smsbackend.actions;

import com.lambdanum.smsbackend.command.CommandContext;
import com.lambdanum.smsbackend.command.CommandHandler;
import com.lambdanum.smsbackend.command.CommandListener;
import com.lambdanum.smsbackend.command.UserRoleEnum;
import com.lambdanum.smsbackend.database.UserDAO;
import com.lambdanum.smsbackend.identity.User;
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
        commandContext.reply("Hello! ");
    }


    @CommandHandler(value = "hello name is <str> ...", requiredRole = UserRoleEnum.NOBODY)
    public void registerUserWithName(CommandContext commandContext, List<String> name) {
        User user = commandContext.getUser();
        user.setName(StringUtils.join(name, " "));
        user.addRole(UserRoleEnum.INTRODUCED);
        userDAO.persist(user);
        commandContext.reply(String.format("Hello %s." , StringUtils.join(name, " ")));

        try {
            if (name.get(0).equals("Harry") && name.get(1).equals("Potter")) {
                commandContext.reply("My name is Tom Riddle");
            }
        } catch (Exception e) { }
    }

    @CommandHandler(value = "secret", requiredRole = UserRoleEnum.INTRODUCED)
    public void secretCommand(CommandContext context){
        context.reply("You found the secret! Foobar! 42! Emily faked cancer once yahoO0OO0OoOo!");
    }
}
