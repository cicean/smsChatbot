package com.lambdanum.smsbackend.command;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

@CommandListener
public class SayHelloCommand {

    @CommandHandler(command = "say hello <int>")
    public void execute(int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("Hello");
        }
    }

    @CommandHandler(command = "say <str> hello <int>")
    public void namedHello(String name, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("Hello " + name + "!");
        }
    }

    @CommandHandler(command = "tell <str> ... hello")
    public void helloArrayParam(List<String> name) {
        System.out.println(StringUtils.join(name, " "));
    }

    @CommandHandler(command = "hello <str> ... <int> time")
    public void helloArrayParamIntExitToken(List<String> name, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(StringUtils.join(name, " "));
        }
    }
}
