package com.example.storyhoster.Model;

public class PostModel {
    String pTitle;
    String pDescription;
    String pTime;
    String pImage;






    public PostModel() {
    }

    //here added setter and getter method to get the image from the firebase--on sunday before lunch;




    public PostModel(String pTitle, String pDescription, String pTime,String pImage) {
        this.pTitle = pTitle;
        this.pDescription = pDescription;
        this.pTime=pTime;
        this.pImage=pImage;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }


    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
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
