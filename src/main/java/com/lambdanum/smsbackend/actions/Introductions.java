package com.lambdanum.smsbackend.actions;

import com.lambdanum.smsbackend.command.CommandContext;
import com.lambdanum.smsbackend.command.CommandHandler;
import com.lambdanum.smsbackend.command.CommandListener;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@CommandListener
public class Introductions {

    @CommandHandler("hello")
    public void registerUser(CommandContext commandContext) {
        commandContext.reply("Hello! Here is an example of my skills.");
    }
    @CommandHandler("hello name is <str> ...")
    public void registerUserWithName(CommandContext commandContext, List<String> name) {
        commandContext.reply(String.format("Hello %s!" , StringUtils.join(name, " ")));
        try {
            if (name.get(0).equals("Harry") && name.get(1).equals("Potter")) {
                commandContext.reply("My name is Tom Riddle");
            }
        } catch (Exception e) {

        }
    }
}
