package com.lambdanum.smsbackend.identity;

import com.lambdanum.smsbackend.database.Entity;
import com.lambdanum.smsbackend.messaging.MessageProviderEnum;

public class User extends Entity {

    public String getContact() {
        return getString("contact");
    }
    public void setContact(String contact) {
        set("contact", contact);
    }

    public String getName() {
        return getString("name");
    }
    public void setName(String name) {
        set("name", name);
    }
    public MessageProviderEnum getMessageProvider() {
        return MessageProviderEnum.getEnum(getInteger("provider_id"));
    }

    public void setMessageProvider(MessageProviderEnum messageProvider) {
        set("provider_id", messageProvider.getId());
    }

    public boolean hasRole(String role) {
        return getString(role).equals('Y');
    }

    public void addRole(String role) {
        set("role", 'Y');
    }
    public void removeRole(String role) {
        set("role",'N');
    }
}
