package com.lambdanum.smsbackend.controller;

import com.lambdanum.smsbackend.sms.SmsLineFacade;
import com.lambdanum.smsbackend.sms.model.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    private SmsLineFacade smsLineFacade;

    @RequestMapping("/sms_received")
    public String processIncomingMessage(@RequestParam(name = "id") Long id) {
        System.out.println(String.format("Received id %s",id.toString()));

        Sms incomingMessage = smsLineFacade.getSms(id);

        Sms response = new Sms();

        response.setDestination(incomingMessage.getDestination());
        response.setMessage("Spring has received your message. Your number: " + id.toString());

        if (smsLineFacade.sendSms(response).isSuccessful()) {
            return "OK";
        } else {
            System.out.println("Error while sending sms.");
            return "Error";
        }
    }


}
