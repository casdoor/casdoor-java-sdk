package org.casbin.casdoor.util;


public enum RoleOperations {
    UPDATE_ROLE("update-role"),
    ADD_ROLE("add-role"),
    DELETE_ROLE("delete-role");

    private final String operation;

    RoleOperations(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
