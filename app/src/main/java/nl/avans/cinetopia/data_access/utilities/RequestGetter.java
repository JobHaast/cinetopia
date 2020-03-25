package nl.avans.cinetopia.data_access.utilities;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;


import nl.avans.cinetopia.domain.Movie;

public class RequestGetter extends AsyncTask {
    public static final String TAG = RequestGetter.class.getSimpleName();
    private ArrayList<Movie> movies;

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    //Method for retrieving the details of a movie
    private Movie getMovieDetailsFromUrl(URL url) {
        return null;
    }

    //Method for retrieving a list of popular movies
    private ArrayList<Movie> getPopularMoviesFromUrl(URL url){
        return null;
    }

    //Method for getting a request token
    private String requestTokenFromUrl(URL url){
        return null;
    }

    //Method for authorizing a request token
    private void authorizeTokenFromUrl(URL url){

    }

    //Method for parsing the results
    private ArrayList<Movie> doParseJsonResult(String jsonResult) {
       return null;
    }

}
