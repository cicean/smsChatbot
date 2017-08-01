package com.lambdanum.smsbackend.controller;

import com.lambdanum.smsbackend.messaging.Message;
import com.lambdanum.smsbackend.nlp.TokenizerService;
import com.lambdanum.smsbackend.sms.SmsProvider;
import com.lambdanum.smsbackend.sms.model.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    private SmsProvider smsProvider;

    @Autowired
    private TokenizerService tokenizerService;

    @RequestMapping("/sms_received")
    public String processIncomingMessage(@RequestParam(name = "id") Long id) {
        System.out.println(String.format("Received id %s",id.toString()));

        Message incomingMessage = smsProvider.getMessage(id);

        Sms response = new Sms();

        response.setDestination(incomingMessage.getDestination());
        response.setContent("Spring has received your message. Your number: " + id.toString());


        System.out.println(tokenizerService.tokenizeAndStem(incomingMessage.getContent()));


        if (smsProvider.sendMessage(response).isSuccessful()) {
            return "OK";
        } else {
            System.out.println("Error while sending sms.");
            return "Error";
        }



    }


}
