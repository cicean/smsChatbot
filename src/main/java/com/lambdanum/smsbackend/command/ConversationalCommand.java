package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.command.tree.DecisionNode;

import java.time.ZonedDateTime;

public class ConversationalCommand {

    private DecisionNode rootNode;
    private ZonedDateTime additionDate;

    public ConversationalCommand(DecisionNode rootNode) {
        this.rootNode = rootNode;
        this.additionDate = ZonedDateTime.now();
    }

    public boolean isExpired() {
        return additionDate.plusMinutes(2).isBefore(ZonedDateTime.now());
    }

    public DecisionNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(DecisionNode rootNode) {
        this.rootNode = rootNode;
    }

    public ZonedDateTime getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(ZonedDateTime additionDate) {
        this.additionDate = additionDate;
    }
}
