package com.lambdanum.smsbackend.sms;

import com.lambdanum.smsbackend.sms.model.SendSmsResult;
import com.lambdanum.smsbackend.sms.model.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SmsLineFacade {

    @Autowired
    private RestTemplate restTemplate;

    private static final String SMS_LINE_URL = "http://sms-line1.internal.lambdanum.com";

    public Sms getSms(Long id) {
        return restTemplate.getForObject(SMS_LINE_URL + "/get_message.py?message_id=" + id.toString(),Sms.class);
    }

    public SendSmsResult sendSms(Sms message) {
        return restTemplate.getForObject(SMS_LINE_URL + String.format("/send_message.py?destination=%s&message=%s",message.getDestination(), message.getMessage()),SendSmsResult.class);
    }
}
