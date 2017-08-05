package com.lambdanum.smsbackend.database;

import com.lambdanum.smsbackend.identity.User;
import com.lambdanum.smsbackend.messaging.MessageProviderEnum;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO implements DAO<User> {

    private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE_URL = "jdbc:sqlite:/home/kento/identity.db";
    private static final String DATABASE_USERNAME = "";
    private static final String DATABASE_PASSWORD = "";

    @Autowired
    public UserDAO() {

    }

    @Override
    public User findById(String id) {

        return User.findById(id);
    }

    @Override
    public List<User> findWhere(String whereClause) {
        return User.where(whereClause);
    }

    @Override
    public List<User> findWhere(String whereClause, Object... params) {
        return User.where(whereClause,params);
    }

    @Override
    public void persist(User entity) {
        //Base.open(DATABASE_DRIVER, DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        entity.saveIt();
        //Base.commitTransaction();
    }

    public User findByContact(String userContact, MessageProviderEnum messageProviderEnum) {
        Base.open(DATABASE_DRIVER, DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        List<User> users = User.where("contact = " + userContact + " and provider_id=" + messageProviderEnum.getId());
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
}
