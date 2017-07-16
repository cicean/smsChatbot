package com.lambdanum.smsbackend.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Sms {

    private Long id;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getFromMe() {
        return isOutbound;
    }

    public void setFromMe(Boolean fromMe) {
        isOutbound = fromMe;
    }
}
