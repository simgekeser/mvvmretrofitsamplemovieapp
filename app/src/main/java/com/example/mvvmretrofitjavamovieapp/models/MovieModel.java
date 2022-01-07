package com.example.mvvmretrofitjavamovieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    private int id;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private String title;
    private float voteAverage;

    public MovieModel(int id, String overview, String posterPath, String releaseDate, String title, float voteAverage) {
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    protected MovieModel(Parcel in) {
        id = in.readInt();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        voteAverage = in.readFloat();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(title);
        dest.writeFloat(voteAverage);
    }
}
