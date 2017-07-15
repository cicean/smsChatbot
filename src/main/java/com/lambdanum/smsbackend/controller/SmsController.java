package com.lambdanum.smsbackend.controller;

import com.lambdanum.smsbackend.SMS.Sms;
import com.lambdanum.smsbackend.SMS.SmsLineFacade;
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

        smsLineFacade.sendSms(response);

        return "OK";
    }


}
