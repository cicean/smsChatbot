package com.lambdanum.smsbackend.identity;

import com.lambdanum.smsbackend.command.UserRoleEnum;
import com.lambdanum.smsbackend.messaging.MessageProviderEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    private String contact;
    private String name;
    private int id;
    private MessageProviderEnum messageProviderEnum;

    private Set<UserRoleEnum> userRoles = new HashSet<>();

    @Column(name = "CONTACT", length = 50)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "PROVIDER_ID", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public MessageProviderEnum getMessageProvider() {
        return messageProviderEnum;
    }

    public void setMessageProvider(MessageProviderEnum messageProvider) {
        this.messageProviderEnum = messageProvider;
    }

    @ElementCollection(targetClass = UserRoleEnum.class)
    @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "ROLE_ID", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Set<UserRoleEnum> getUserRoles() {
        return this.userRoles;
    }
    public void setUserRoles(Set<UserRoleEnum> userRoles) {
        this.userRoles = userRoles;
    }

    @Transient
    public boolean hasRole(UserRoleEnum role) {
        return userRoles.contains(role);
    }

    @Transient
    public void addRole(UserRoleEnum role) {
        userRoles.add(role);
    }

}
