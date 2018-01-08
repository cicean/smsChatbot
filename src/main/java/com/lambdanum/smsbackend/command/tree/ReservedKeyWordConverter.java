package com.lambdanum.smsbackend.command.tree;

public interface ReservedKeyWordConverter {

    boolean isMatching(String value);

    Object getMatchingObject(String value);

    String getMatchedToken();

}
