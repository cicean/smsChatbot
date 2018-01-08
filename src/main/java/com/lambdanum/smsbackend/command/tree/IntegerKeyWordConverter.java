package com.lambdanum.smsbackend.command.tree;


import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class IntegerKeyWordConverter implements ReservedKeyWordConverter {

    @Override
    public boolean isMatching(String value) {
        return NumberUtils.isDigits(value);
    }

    @Override
    public Object getMatchingObject(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String getMatchedToken() {
        return "<int>";
    }
}
