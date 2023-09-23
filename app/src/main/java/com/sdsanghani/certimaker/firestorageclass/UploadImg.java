package com.sdsanghani.certimaker.firestorageclass;

import android.net.Uri;

public class UploadImg {

    String name;
    String imgUrl;

    public UploadImg() {
    }

    public UploadImg(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
