package com.lambdanum.smsbackend.sms.model;

public class SendSmsResult {

    public String getError() {
        return error;
    }

    public Boolean isSuccessful() {
        return "None".equals(error);
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

}
