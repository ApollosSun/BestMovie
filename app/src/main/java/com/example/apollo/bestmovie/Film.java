package com.example.apollo.bestmovie;

import java.util.ArrayList;

public class Film {

    private String vote;
    private String title;
    private String image;
    private String overview;
    private String releaseDate;

    public Film(String vote, String title, String image, String overview, String releaseDate){
        this.vote = vote;
        this.title = title;
        this.image = image;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public String getVote() {
        return vote;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
