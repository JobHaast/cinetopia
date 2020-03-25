package nl.avans.cinetopia.domain;

import java.util.ArrayList;

public class Movie {
    private static final String TAG = Movie.class.getSimpleName();

    private int id;
    private String title;
    private String overview;
    private String imageURL;
    private String releaseDate;
    private String language;
    private String runtime;
    private Rating rating;
    private ArrayList<Genre> genres;

    public Movie(int id, String title, String imageURL, Rating rating, ArrayList<Genre> genres){
        this(id, title, "", imageURL, "", "", "", rating, genres);
    }

    public Movie(int id, String title, String overview, String imageURL, String releaseDate, String language, String runtime, Rating rating, ArrayList<Genre> genres){
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.imageURL = imageURL;
        this.releaseDate = releaseDate;
        this.language = language;
        this.runtime = runtime;
        this.rating = rating;
        this.genres = genres;
    }

    public static String getTAG() {
        return TAG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
