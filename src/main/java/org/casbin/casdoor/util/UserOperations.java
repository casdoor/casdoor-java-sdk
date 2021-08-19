package org.casbin.casdoor.util;

public enum UserOperations {
    GET_USER("get-user"),
    ADD_USER("add-user"),
    DELETE_USER("delete-user"),
    UPDATE_USER("update-user");

    private final String operation;
    UserOperations(String op) {
        this.operation = op;
    }

    public String getOperation() {
        return operation;
    }
}
