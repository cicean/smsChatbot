package com.lambdanum.smsbackend.messaging;

import com.lambdanum.smsbackend.sms.SmsProvider;

public enum MessageProviderEnum {
    UNKNOWN(0, "unknown", null),
    SMS(1,"SmsProvider", SmsProvider.class),
    ;

    private int id;
    private String name;
    private Class<? extends MessageProvider> providerClass;

    MessageProviderEnum(int id, String name, Class<? extends MessageProvider> providerClass) {
        this.id = id;
        this.name = name;
        this.providerClass = providerClass;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Class<? extends MessageProvider> getProviderClass() {
        return providerClass;
    }

    public static MessageProviderEnum getEnum(int id) {
        for (MessageProviderEnum provider : values()) {
            if (provider.id == id) {
                return provider;
            }
        }
        return UNKNOWN;
    }


}
