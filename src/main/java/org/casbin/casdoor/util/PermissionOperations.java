package org.casbin.casdoor.util;


public enum PermissionOperations {
    GET_PERMISSION("get-permission"),
    ADD_PERMISSION("add-permission"),
    DELETE_PERMISSION("delete-permission"),
    UPDATE_PERMISSION("update-permission");
    private final String operation;
    PermissionOperations(String op) {
        this.operation = op;
    }

    public String getOperation() {
        return operation;
    }
}
