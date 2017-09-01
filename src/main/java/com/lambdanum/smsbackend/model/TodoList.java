package com.lambdanum.smsbackend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TODO_LISTS")
public class TodoList {

    private int id;
    private int userId;
    private String name;
    private List<TodoItem> items;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LIST_ID", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = TodoItem.class, mappedBy = "todoList")
    public List<TodoItem> getItems() {
        return items;
    }

    public void setItems(List<TodoItem> items) {
        this.items = items;
    }

    @Transient
    public void addItem(TodoItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        item.setTodoList(this);
        items.add(item);
    }
}
