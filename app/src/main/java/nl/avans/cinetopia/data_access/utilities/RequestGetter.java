package nl.avans.cinetopia.data_access.utilities;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;
import nl.avans.cinetopia.domain.Rating;

public class RequestGetter extends AsyncTask<URL, Void, ArrayList<Movie>> {

    public static final String TAG = RequestGetter.class.getSimpleName();

    private ArrayList<Movie> movies;

    //Moet hier trouwens geen listener komen? - Iza

    //Final static strings needed to parse the JSONResults
    final static String JSON_RESULT = "results";
    final static String JSON_MOVIE_ID = "id";
    final static String JSON_MOVIE_TITLE = "title";
    final static String JSON_MOVIE_POSTER_REQUEST_KEY = "https://image.tmdb.org/t/p/w500";
    final static String JSON_MOVIE_POSTER_PATH = "poster_path";
    final static String JSON_MOVIE_GENRE_IDS = "genre_ids";
    final static String JSON_MOVIE_VOTE_AVERAGE = "vote_average";


    // TODO - Methode doInBackground werkend maken
    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {
        return null;
    }

    // TODO - Methode getMovieDetailsFromUrl werkend maken
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

    // TODO - Methode requestTokenFromUrl werkend maken
    //Method for getting a request token
    private String requestTokenFromUrl(URL url) {
        return null;
    }

    // TODO - Methode authorizeTokenFromUrl werkend maken
    //Method for authorizing a request token
    private void authorizeTokenFromUrl(URL url) {

    }

    //Method for parsing the results
    private ArrayList<Movie> doParseJsonResultToPopularMovieList(String JSONResult) {
        Log.d(TAG, "doParseJsonResultToPopularMovieList aangeroepen");

        ArrayList<Movie> popularMovieResult = new ArrayList<>();
        //The JSONobject is parsed to different String variables and an ArrayList
        try {
            JSONObject results = new JSONObject(JSONResult);
            JSONArray movies = results.getJSONArray(JSON_RESULT);

//            Movie(int id, String title, String imageURL, Rating rating, ArrayList<Genre> genres)
            for (int i = 0; i < movies.length(); i++) {
                JSONObject movie = (JSONObject) movies.get(i);
                int id = movie.getInt(JSON_MOVIE_ID);
                String title = movie.getString(JSON_MOVIE_TITLE);
                String moviePosterPath = movie.getString(JSON_MOVIE_POSTER_PATH);
                String moviePosterUrl = JSON_MOVIE_POSTER_REQUEST_KEY + moviePosterPath;
                Rating movieRating = new Rating(movie.getDouble(JSON_MOVIE_VOTE_AVERAGE));


                // TODO - Het werkend maken van de JSON parse voor genre > belangrijk!
                //Putting the genreIds and titles into an ArrayList
                JSONArray genreIds = movie.getJSONArray(JSON_MOVIE_GENRE_IDS);
                ArrayList<Genre> genres = new ArrayList<>();
                for (int x = 0; x < genreIds.length(); x++) {
                    int genre = genreIds.getInt(x);

                    //DIT MOET NOG VERANDERD WORDEN NAAR EEN WAARDE UIT EEN HASHMAP
                    String genreTitle = "test";
                    //DIT MOET NOG VERANDERD WORDEN NAAR EEN WAARDE UIT EEN HASHMAP



                    if (genre > 0 && !genreTitle.equals("null") || genreTitle != null ) {
                        genres.add(new Genre(genre, title));
                    }
                }

                //Movies get instantiated and added to the popularMovieResult ArrayList
                Movie newMovie = new Movie(id, title, moviePosterUrl, movieRating, genres);
                popularMovieResult.add(newMovie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSONException: " + e.getMessage());
        }

        Log.i(TAG, "Aantal movies: " + popularMovieResult.size());
        return popularMovieResult;
    }

}
