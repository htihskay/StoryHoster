package com.example.storyhoster.Model;

public class PostModel {
    String pTitle,pDescription,pTime;

    public PostModel() {
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public PostModel(String pTitle, String pDescription, String pTime) {
        this.pTitle = pTitle;
        this.pDescription = pDescription;
        this.pTime=pTime;
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
