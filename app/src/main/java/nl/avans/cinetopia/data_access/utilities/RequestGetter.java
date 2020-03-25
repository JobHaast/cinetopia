package nl.avans.cinetopia.data_access.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


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
    private ArrayList<Movie> getPopularMoviesFromUrl() {
        Log.d(TAG, "getPopularMoviesFromUrl aangeroepen");

        ArrayList<Movie> popularMovieList = new ArrayList<>();

        //The first 200 movies are retrieved from the API
        try {
            for (int i = 1; i < 200; i++) {
                URL url = UrlBuilder.buildPopularMovieListUrl(i);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");

                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        popularMovieList.addAll(doParseJsonResultToPopularMovieList(scanner.next()));
                    }
                } finally {
                    Log.d(TAG, "getResultFromUrl is done with page: " + i + " and is going to disconnect");
                    urlConnection.disconnect();
                }
            }


        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        }
        return popularMovieList;
    }

    //Method for getting a request token
    private String requestTokenFromUrl(URL url) {
        return null;
    }

    //Method for authorizing a request token
    private void authorizeTokenFromUrl(URL url) {

    }

    //Method for parsing the results
    private ArrayList<Movie> doParseJsonResultToPopularMovieList(String jsonResult) {
        return null;
    }

}
