package com.lambdanum.smsbackend.command;

public enum UserRoleEnum {
    NOBODY(0),
    INTRODUCED(1),
    ADMIN(2);

    private int id;

    UserRoleEnum(int id) {
        this.id = id;
    }
}
