package com.lambdanum.smsbackend.database;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBootstrapper {


    private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE_URL = "jdbc:sqlite:/home/kento/identity.db";
    private static final String DATABASE_USERNAME = "";
    private static final String DATABASE_PASSWORD = "";

    @Autowired
    public DatabaseBootstrapper() {
        //Base.open(DATABASE_DRIVER, DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }


}
