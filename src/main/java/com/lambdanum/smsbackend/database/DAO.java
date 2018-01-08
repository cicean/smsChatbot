package com.lambdanum.smsbackend.database;

public interface DAO<T> {

    T findById(int id);

    void persist(T entity);

}
