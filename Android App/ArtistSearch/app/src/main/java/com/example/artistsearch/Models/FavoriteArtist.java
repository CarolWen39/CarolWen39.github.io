package com.example.artistsearch.Models;

public class FavoriteArtist {
    String name;
    String id;
    String nation;
    String birthday;

    public FavoriteArtist(String name, String id, String nation, String birthday) {
        this.name = name;
        this.id = id;
        this.nation = nation;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getNation() {
        return nation;
    }

    public String getBirthday() {
        return birthday;
    }
}
