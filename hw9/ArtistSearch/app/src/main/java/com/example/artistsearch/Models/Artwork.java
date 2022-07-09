package com.example.artistsearch.Models;

public class Artwork {
    String artworkId;
    String imgUrl;
    String artworkName;
    String cateTitle;
    String cateImg;
    String catedesc;

    public  Artwork(String artworkId, String imgUrl, String artworkName) {
        this.artworkId = artworkId;
        this.imgUrl = imgUrl;
        this.artworkName = artworkName;
    }
    public Artwork(String artworkId, String imgUrl, String artworkName, String cateTitle, String cateImg, String catedesc) {
        this.artworkId = artworkId;
        this.imgUrl = imgUrl;
        this.artworkName = artworkName;
        this.cateTitle = cateTitle;
        this.cateImg = cateImg;
        this.catedesc = catedesc;
    }

    public String getArtworkId() {
        return artworkId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getArtworkName() {
        return artworkName;
    }
}
