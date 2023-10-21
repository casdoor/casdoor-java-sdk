package org.casbin.casdoor.util;

/*
@author zhangJH
@create 2023-10-20-8:33
*/


public enum CertOperations {
    ADD_CERT("add-cert"),
    DELETE_CERT("delete-cert"),
    UPDATE_CERT("update-cert");
    private final String operation;

    CertOperations(String op) {
        this.operation = op;
    }

    public String getOperation() {
        return operation;
    }
}
