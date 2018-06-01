package com.example.apollo.bestmovie;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Film implements Parcelable {

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

    private Film(Parcel in){
        vote = in.readString();
        title = in.readString();
        image = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Could be a string

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vote);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>(){

        @Override
        public Film createFromParcel(Parcel source) {
            return new Film (source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

}
