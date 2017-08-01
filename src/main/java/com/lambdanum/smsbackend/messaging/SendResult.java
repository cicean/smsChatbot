package com.lambdanum.smsbackend.messaging;

public abstract class SendResult {

    public abstract boolean isSuccessful();

    public abstract String getError();

}
