package com.sdsanghani.certimaker.html_certi.data_models;

public class UserDetails {

    String name ,email;

    public UserDetails() {
    }


    public UserDetails(String name, String email) {
        this.name = name;
        this.email = email;
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
