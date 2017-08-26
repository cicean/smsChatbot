package com.lambdanum.smsbackend.sms;

import com.lambdanum.smsbackend.messaging.Message;
import com.lambdanum.smsbackend.messaging.MessageProvider;
import com.lambdanum.smsbackend.messaging.SendResult;
import com.lambdanum.smsbackend.sms.model.SendSmsResult;
import com.lambdanum.smsbackend.sms.model.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SmsProvider implements MessageProvider {

    @Autowired
    private RestTemplate restTemplate;

    private static final String SMS_LINE_URL = "http://sms-line1.internal.lambdanum.com";

    @Override
    public SendResult sendMessage(Message message) {
        return restTemplate.getForObject(SMS_LINE_URL + String.format("/send_message.py?destination=%s&message=%s",
                        message.getDestination(), message.getContent()),SendSmsResult.class);
    }

    @Override
    public Message getMessage(Long id) {
        return restTemplate.getForObject(SMS_LINE_URL + "/get_message.py?message_id=" + id.toString(),Sms.class);
    }
}
