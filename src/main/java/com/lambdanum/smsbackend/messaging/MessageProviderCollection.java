package com.lambdanum.smsbackend.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class MessageProviderCollection {

    private HashMap<Class<? extends MessageProvider>, MessageProvider> providers;

    @Autowired
    public MessageProviderCollection(List<MessageProvider> messageProviders) {
        providers = new HashMap<>();
        messageProviders.stream().forEach(provider -> providers.put(provider.getClass(), provider));
    }

    public MessageProvider getMessageProvider(MessageProviderEnum messageProviderEnum) {
        return providers.get(messageProviderEnum.getProviderClass());
    }
}
