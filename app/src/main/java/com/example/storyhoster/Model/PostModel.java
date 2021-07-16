package com.example.storyhoster.Model;

public class PostModel {
    String pTitle,pDescription;

    public PostModel() {
    }

    public PostModel(String pTitle, String pDescription) {
        this.pTitle = pTitle;
        this.pDescription = pDescription;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }
}
