package com.lambdanum.smsbackend.identity;

import com.lambdanum.smsbackend.database.UserDAO;
import com.lambdanum.smsbackend.messaging.MessageProviderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getOrCreateUser(String contact, MessageProviderEnum messageProvider) {
        User user = userDAO.findByContact(contact,messageProvider);
        if (user == null) {
            user = new User();
            user.setContact(contact);
            user.setMessageProvider(messageProvider);
            userDAO.persist(user);
        }
        return user;
    }

}
