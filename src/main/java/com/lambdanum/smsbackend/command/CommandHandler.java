package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.identity.UserRoleEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandHandler {

    String value();

    UserRoleEnum requiredRole() default UserRoleEnum.INTRODUCED;

}
