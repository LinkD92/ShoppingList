package com.symbol.shoppinglistv2.Components;

public class SharedMember {
    private String email;
    private String path;
    private String uid;

    public SharedMember() {
    }

    public SharedMember(String uid, String email, String path) {
        this.email = email;
        this.path = path;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

