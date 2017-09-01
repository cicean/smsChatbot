package com.lambdanum.smsbackend.database;

import com.lambdanum.smsbackend.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class TodoListDAO implements DAO<TodoList> {

    private EntityManager em;

    @Autowired
    public TodoListDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public TodoList findById(int id) {
        return em.find(TodoList.class, id);
    }

    @Override
    public void persist(TodoList entity) {
        em.persist(entity);
    }

    public List<TodoList> getTodoListsForUser(int userId) {
        return em.createNativeQuery("select * from TODO_LISTS where user_id=:userId", TodoList.class).setParameter("userId",userId).getResultList();
    }

    public List<TodoList> getTodoListForUserById(int userId, String listName) {
        return em.createNativeQuery("select * from TODO_LISTS where user_id=:userId and name=:listName",TodoList.class)
                        .setParameter("userId",userId)
                        .setParameter("listName",listName).getResultList();
    }
}
