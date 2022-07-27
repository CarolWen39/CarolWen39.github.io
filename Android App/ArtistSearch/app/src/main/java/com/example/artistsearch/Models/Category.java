package com.example.artistsearch.Models;

public class Category {
    String name;
    String img;
    String descrption;

    public Category(String name, String img, String descrption) {
        this.name = name;
        this.img = img;
        this.descrption = descrption;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDescrption() {
        return descrption;
    }
}
