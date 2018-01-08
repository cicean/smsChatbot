package com.lambdanum.smsbackend.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Conversational {

    Class<?> value();

}
