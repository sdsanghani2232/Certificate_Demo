package com.sdsanghani.certimaker.html_certi.datamodels;

public class HtmlDataModel {

    String fileName;
    String fileDownloadUrl;

    public HtmlDataModel() {
    }
    public HtmlDataModel(String fileName, String fileDownloadUrl) {
        this.fileName = fileName;
        this.fileDownloadUrl = fileDownloadUrl;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }


}
