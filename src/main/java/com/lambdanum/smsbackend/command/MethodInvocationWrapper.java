package com.lambdanum.smsbackend.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvocationWrapper {

    private Object object;
    private Method method;

    public MethodInvocationWrapper(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Object invoke(Object...args) {
        try {
            method.invoke(object,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
