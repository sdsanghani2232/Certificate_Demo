package com.sdsanghani.certimaker.firestore.models;
//model class
public class UserModel {

    String name,email,id;

    public UserModel() {
    }

    public UserModel(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

