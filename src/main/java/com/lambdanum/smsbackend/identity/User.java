package com.lambdanum.smsbackend.identity;

import com.lambdanum.smsbackend.command.UserRole;
import com.lambdanum.smsbackend.database.Entity;
import com.lambdanum.smsbackend.messaging.MessageProviderEnum;
import org.javalite.activejdbc.annotations.Table;

@Table("users")
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

    public boolean hasRole(UserRole role) {
        return "Y".equals(getString(role.name()));
    }

    public void addRole(UserRole role) {
        set(role.name(), 'Y');
    }
    public void removeRole(UserRole role) {
        set(role.name(),'N');
    }
}
