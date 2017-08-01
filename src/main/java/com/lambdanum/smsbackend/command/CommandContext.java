package com.lambdanum.smsbackend.command;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandContext {

    private ArrayList<Object> args = new ArrayList<>();

    public void addArgument(Object...args) {
        this.args.addAll(Arrays.asList(args));
    }

    public Object[] getArgs() {
        return args.toArray();
    }

}
