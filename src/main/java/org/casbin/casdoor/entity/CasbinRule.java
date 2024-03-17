package org.casbin.casdoor.entity;

public class CasbinRule {
    public long id;
    public String Ptype;
    public String V0;
    public String V1;
    public String V2;
    public String V3;
    public String V4;
    public String V5;
    public String tableName;

    public CasbinRule(long id, String ptype, String v0, String v1, String v2, String v3, String v4, String v5, String tableName) {
        this.id = id;
        this.Ptype = ptype;
        this.V0 = v0;
        this.V1 = v1;
        this.V2 = v2;
        this.V3 = v3;
        this.V4 = v4;
        this.V5 = v5;
        this.tableName = tableName;
    }

    public CasbinRule(){

    }

}
