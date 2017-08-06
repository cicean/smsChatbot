package com.lambdanum.smsbackend.command;

public enum UserRole {
    NOBODY(0),
    INTRODUCED(1),
    ADMIN(2);

    private int id;

    UserRole(int id) {
        this.id = id;
    }
}
