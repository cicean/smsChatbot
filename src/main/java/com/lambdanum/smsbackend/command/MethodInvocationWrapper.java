package com.lambdanum.smsbackend.command;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvocationWrapper {

    private Object object;
    private Method method;
    private final boolean contextual;

    public MethodInvocationWrapper(Object object, Method method) {
        this.object = object;
        this.method = method;
        contextual = method.getParameterTypes().length > 0 && method.getParameterTypes()[0].equals(CommandContext.class);
    }

    public Object invoke(CommandContext context, Object...args) {
        try {
            if (contextual) {
                Object[] contextArray = {context};
                return method.invoke(object,ArrayUtils.addAll(contextArray, args));
            } else {
                return method.invoke(object, args);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isContextual() {
        return contextual;
    }

    public String getMethodName() {
        return method.getName();
    }
}
