package com.lambdanum.smsbackend.messaging;


import java.util.Date;

public interface Message {

    String getContent();
    String getDestination();
    Date getDate();
    Boolean isOutbound();

}
