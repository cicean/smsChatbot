package com.lambdanum.smsbackend.sms.model;

import com.lambdanum.smsbackend.messaging.SendResult;

public class SendSmsResult extends SendResult {

    public String getError() {
        return error;
    }

    public boolean isSuccessful() {
        return "None".equals(error);
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

}
