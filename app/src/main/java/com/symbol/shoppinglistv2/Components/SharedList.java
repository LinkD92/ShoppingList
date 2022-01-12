package com.symbol.shoppinglistv2.Components;

public class SharedList {
    private String email;
    private String uid;
    private String name;
    private long update;

    public SharedList() {
    }

    public SharedList(String email, String uid, String name, long update) {
        this.email = email;
        this.uid = uid;
        this.name = name;
        this.update = update;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUpdate() {
        return update;
    }

    public void setUpdate(long update) {
        this.update = update;
    }
}
