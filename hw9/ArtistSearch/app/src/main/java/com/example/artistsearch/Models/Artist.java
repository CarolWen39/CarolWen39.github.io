package com.example.artistsearch.Models;

public class Artist {
    private String name;
    private String image;
    private String id;

    public Artist(String name, String image, String id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public String getImage() {

        return image;
    }

    public String getId() {
        return id;
    }
}
