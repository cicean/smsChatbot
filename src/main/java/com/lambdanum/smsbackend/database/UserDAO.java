package com.lambdanum.smsbackend.database;

import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.messaging.MessageProviderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class UserDAO implements DAO<User> {

    private EntityManager entityManager;

    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Deprecated
    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void persist(User entity) {
        entityManager.persist(entity);
    }

    public User findByContact(String userContact, MessageProviderEnum messageProviderEnum) {
        List<User> users = entityManager.createQuery("from User where contact=:contact and messageProvider=:provider", User.class)
                        .setParameter("contact",userContact)
                        .setParameter("provider", messageProviderEnum).getResultList();

        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);

    }
}
