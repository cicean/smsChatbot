package com.lambdanum.smsbackend.messaging;


import java.util.Date;

public interface Message {

    String getContent();
    String getSource();
    String getDestination();
    Date getDate();
    Boolean isOutbound();

    MessageProviderEnum getMessageProvider();

    Message createResponseMessage(String destination, String content);

}
