package com.lambdanum.smsbackend.sms;

import com.lambdanum.smsbackend.messaging.Message;
import com.lambdanum.smsbackend.messaging.MessageReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    private static Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsProvider smsProvider;

    @Autowired
    private MessageReplyService messageReplyService;

    @RequestMapping("/sms_received")
    public void processIncomingMessage(@RequestParam(name = "id") Long id) {
        logger.info(String.format("Received id %s",id.toString()));

        Message incomingMessage = smsProvider.getMessage(id);

        messageReplyService.replyToMessage(incomingMessage);


    }


}
