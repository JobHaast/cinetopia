package nl.avans.cinetopia.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {

    private int id;
    private String title;
    private String overview;
    private String imageUrl;
    private String backdropUrl;
    private String releaseDate;
    private int runtime;
    private double rating;
    private ArrayList<Genre> genres;
    private String website;

    public Movie() {

    }

    public Movie(int id, String title, String imageUrl, double rating, ArrayList<Genre> genres) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.genres = genres;
    }

    public Movie(int id, String title, String overview, String imageUrl, String backdropUrl, String releaseDate,
                 int runtime, double rating, ArrayList<Genre> genres, String website) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.imageUrl = imageUrl;
        this.backdropUrl = backdropUrl;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.rating = rating;
        this.genres = genres;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getWebsite() {
        return website;
    }
}
