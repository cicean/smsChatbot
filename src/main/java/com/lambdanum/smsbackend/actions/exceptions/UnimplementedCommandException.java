package com.lambdanum.smsbackend.actions.exceptions;

public class UnimplementedCommandException extends RuntimeException {
    public UnimplementedCommandException(String message) {
        super(message);
    }
}
