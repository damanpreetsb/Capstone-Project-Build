package com.example.daman.capstone;

/**
 * Created by daman on 22/12/16.
 */

public class NewsData {

    private final String moviePoster;
    private final int movieId;

    public NewsData(String moviePoster, int movieId) {
        this.moviePoster = moviePoster;
        this.movieId = movieId;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public int getMovieId() {
        return movieId;
    }

}
