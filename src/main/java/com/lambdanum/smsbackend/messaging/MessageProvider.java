package com.lambdanum.smsbackend.messaging;

public interface MessageProvider {

    SendResult sendMessage(Message message);

    Message getMessage(Long id);

}
