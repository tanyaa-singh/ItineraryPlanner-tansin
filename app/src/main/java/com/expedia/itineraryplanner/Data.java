package com.expedia.itineraryplanner;

public class Data {

    private String imagePath;

    private String id;

    public Data(String imagePath, String id) {
        this.imagePath = imagePath;
        this.id = id;
     }

    public String getImagePath() {
        return imagePath;
    }

    public String getId() {
        return id;
    }
}
