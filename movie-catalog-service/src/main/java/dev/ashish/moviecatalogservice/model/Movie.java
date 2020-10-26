package dev.ashish.moviecatalogservice.model;

public class Movie {
    String movieName;
    String movieId;

    public Movie() {
    }

    public Movie(String movieName, String movieId) {
        this.movieName = movieName;
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
