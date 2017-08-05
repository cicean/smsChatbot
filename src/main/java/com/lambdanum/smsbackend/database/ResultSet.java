package com.lambdanum.smsbackend.database;

import java.util.List;

public class ResultSet {

    private List<Entity> result;

    public ResultSet(List<Entity> result) {
        this.result = result;
    }

    public List<Entity> getResultList() {
        return this.result;
    }

    public <T extends Entity> List<T> getResultList(Class<T> responseClass) {
        return (List<T>)(List<?>)result;
        //List<T> result = new ArrayList<>();
    }
}

