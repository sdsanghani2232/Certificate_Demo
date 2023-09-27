package com.sdsanghani.certimaker.html_certi.data_models;

public class PdfDetails {

    String pdfUrl,PdfName;

    public PdfDetails() {
    }

    public PdfDetails(String pdfUrl, String pdfName) {
        PdfName = pdfName;
        this.pdfUrl = pdfUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfName() {
        return PdfName;
    }

    public void setPdfName(String pdfName) {
        PdfName = pdfName;
    }
}
