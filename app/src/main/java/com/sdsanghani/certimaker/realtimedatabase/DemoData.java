package com.sdsanghani.certimaker.realtimedatabase;
//Data Class
//model part = backend dev..
public class DemoData {

    String name;
    String email;

    public DemoData() {
    }

    public DemoData(String name, String email) {
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
