package com.lambdanum.smsbackend.command;

import org.springframework.stereotype.Component;

@Component
public class FoobarCommand implements CommandListener {

    @CommandHandler(command = "foobar")
    public void foobar() {
        System.out.println("foobar");
    }

    @CommandHandler(command = "foo bar")
    public void notFoobar() {
        System.out.println("foo bar");
    }

}
