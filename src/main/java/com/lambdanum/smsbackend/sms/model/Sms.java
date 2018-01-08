package com.lambdanum.smsbackend.sms.model;

import com.lambdanum.smsbackend.messaging.Message;
import com.lambdanum.smsbackend.messaging.MessageProviderEnum;

import java.util.Date;

public class Sms implements Message {

    private static String MY_NUMBER = "12345678";

    private Long id;
    private String content;
    private String destination;
    private String source;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Boolean isOutbound() {
        return source.equals(MY_NUMBER);
    }

    @Override
    public MessageProviderEnum getMessageProvider() {
        return MessageProviderEnum.SMS;
    }

    @Override
    public Message createResponseMessage(String destination, String content) {
        Sms response = new Sms();
        response.setDestination(destination);
        response.setContent(content);
        response.setSource(MY_NUMBER);
        response.setDate(new Date());
        return response;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
