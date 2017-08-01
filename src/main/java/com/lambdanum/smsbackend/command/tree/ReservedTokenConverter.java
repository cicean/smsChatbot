package com.lambdanum.smsbackend.command.tree;

public interface ReservedTokenConverter {

    boolean isMatching(String value);

    Object getMatchingObject(String value);

    String getMatchedToken();

}
