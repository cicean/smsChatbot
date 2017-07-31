package com.lambdanum.smsbackend.command;

import org.springframework.stereotype.Component;

@Component
public class BazCommand implements CommandListener {

    @CommandHandler(command = "foo bar baz")
    public void executeFoobarBaz() {
        System.out.println("foo bar baz");
    }
}
