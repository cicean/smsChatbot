package com.lambdanum.smsbackend.command;

@CommandListener
public class BazCommand {

    @CommandHandler(command = "foo bar baz")
    public void executeFoobarBaz() {
        System.out.println("foo bar baz");
    }
}
