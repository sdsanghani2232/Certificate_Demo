package com.sdsanghani.certimaker.html_certi.data_models;

public class FilesDetails {

    String code,NewExcel,NewCerti,eventName;

    public String getEventName() {
        return eventName;
    }

    public FilesDetails() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNewExcel(String newExcel) {
        NewExcel = newExcel;
    }

    public void setNewCerti(String newCerti) {
        NewCerti = newCerti;
    }

}
