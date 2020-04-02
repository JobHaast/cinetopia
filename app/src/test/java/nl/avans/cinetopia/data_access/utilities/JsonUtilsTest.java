package nl.avans.cinetopia.data_access.utilities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.PopularMovieGetRequest;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;


import static org.junit.Assert.*;

public class JsonUtilsTest {
    private ArrayList<Movie> response;

    @Test
    public void parseMoviesJson() {
        PopularMovieGetRequest task = new PopularMovieGetRequest(new PopularMovieApiListener());
        task.execute(UrlBuilder.buildPopularMovieListUrl(1));

//        String jsonResult = NetworkUtils.getResponseFromHttpUrl(UrlBuilder.buildPopularMovieListUrl());
//        ArrayList<Movie> result = JsonUtils.parseMoviesJson(jsonResult);
//        ArrayList<Movie> actualResult = new ArrayList<>();
//        ArrayList<Genre> actualGenres = new ArrayList<>();
//        actualGenres.add(new Genre(28, ""));
//        actualGenres.add(new Genre( 53, ""));
//        actualResult.add(new Movie(245891, "John Wick", "/5vHssUeVe25bMrof1HyaPyWgaP.jpg", 7.2, actualGenres));
//        assertEquals(actualResult, response);
    }

    class PopularMovieApiListener implements PopularMovieGetRequest.PopularMovieApiListener {
        @Override
        public void handleMovieResult(ArrayList<Movie> movies) {
            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
            response.clear();
            response.addAll(movies);
        }
    }


    @Test
    public void parseMovieDetailsJson() {
    }

    @Test
    public void parseGenresJson() {
    }

    @Test
    public void parseRequestTokenJson() {
    }

    @Test
    public void parseSessionId() {
    }

    @Test
    public void parseCreateList() {
    }

    @Test
    public void parseListMoviesJson() {
    }

    @Test
    public void parseAddRemoveMovieToListResponse() {
    }
}