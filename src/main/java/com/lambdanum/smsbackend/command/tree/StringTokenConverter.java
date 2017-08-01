package com.lambdanum.smsbackend.command.tree;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class StringTokenConverter implements ReservedTokenConverter {
    @Override
    public boolean isMatching(String value) {
        return StringUtils.isNotBlank(value);
    }

    @Override
    public Object getMatchingObject(String value) {
        return value;
    }

    @Override
    public String getMatchedToken() {
        return "<str>";
    }

}
