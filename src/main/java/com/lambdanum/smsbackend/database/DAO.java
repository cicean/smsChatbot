package com.lambdanum.smsbackend.database;

import java.util.List;

public interface DAO<T extends Entity> {

    T findById(String id);

    List<T> findWhere(String whereClause);

    List<T> findWhere(String whereClause, Object... params);

    void persist(T entity);

}
