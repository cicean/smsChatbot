package com.lambdanum.smsbackend.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lambdanum.smsbackend.messaging.Message;

import java.util.Date;

public class Sms implements Message {

    private Long id;
    @JsonProperty("message")
    private String content;
    @JsonProperty("chat_identifier")
    private String destination;
    private Date date;
    @JsonProperty("is_from_me")
    private Boolean isOutbound;

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
        return isOutbound;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
