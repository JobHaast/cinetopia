package nl.avans.cinetopia.data_access.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class JsonUtils {

    // Tag for logging.
    private static final String TAG = JsonUtils.class.getSimpleName();

    // ArrayList containing the latest genres from TMDB.
    private static ArrayList<Genre> mGenres = new ArrayList<>();

    // Constant variable for JSON array names.
    private static final String ARRAY_RESULTS = "results";
    private static final String ARRAY_GENRES = "genres";
    private static final String ARRAY_GENRE_IDS = "genre_ids";

    // Constant variables for JSON key names.
    private static final String KEY_ID = "id";
    private static final String KEY_GENRE_NAME = "name";
    private static final String KEY_TITLE = "title";
    private static final String KEY_RATING = "vote_average";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_RUNTIME = "runtime";
    private static final String KEY_REQUEST_TOKEN = "request_token";
    private static final String KEY_SESSION_ID = "session_id";
    private static final String KEY_LIST_ID = "list_id";

    /**
     * Parses the JSON result of the movies GET-request and stores the data into new Movie objects.
     *
     * @param jsonResult the JSON response from the server.
     * @return An ArrayList of Movie objects.
     * @throws JSONException If the JSON data cannot be properly parsed.
     */
    public static ArrayList<Movie> parseMoviesJson(String jsonResult) throws JSONException {
        Log.d(TAG, "Method called: parsePopularMoviesJson");

        ArrayList<Movie> movies = new ArrayList<>();

        JSONObject moviesJson = new JSONObject(jsonResult);

        JSONArray arrayResults = moviesJson.getJSONArray(ARRAY_RESULTS);

        // Iterate through the entire array of results, retrieve the data, create new Movie
        // objects and store them into the ArrayList.
        for (int i = 0; i < arrayResults.length(); i++) {
            // Retrieve the result object from the array.
            JSONObject result = arrayResults.getJSONObject(i);

            // Retrieve required data and store it into variables.
            int id = result.getInt(KEY_ID);
            String title = result.getString(KEY_TITLE);
            double rating = result.getDouble(KEY_RATING);
            String imageUrl = UrlBuilder.buildPosterImageUrl(result.getString(KEY_POSTER_PATH));

            JSONArray arrayGenres = result.getJSONArray(ARRAY_GENRE_IDS);
            ArrayList<Integer> genreIds = new ArrayList<>();

            for (int j = 0; j < arrayGenres.length(); j++) {
                genreIds.add(arrayGenres.getInt(j));
            }

            HashMap<Integer, String> genreMap = new HashMap<>();

            for (Genre genre : mGenres) {
                int genreId = genre.getId();
                String genreName = genre.getName();

                genreMap.put(genreId, genreName);
            }

            ArrayList<Genre> genres = new ArrayList<>();

            for (int genreId : genreIds) {
                if (genreMap.containsKey(genreId)) {
                    genres.add(new Genre(genreId, genreMap.get(genreId)));
                }
            }

            movies.add(new Movie(id, title, imageUrl, rating, genres));

        }

        return movies;
    }

    public static Movie parseMovieDetailsJson(String jsonResult) throws JSONException {
        Log.d(TAG, "Method called: parseMovieDetailsJson");

        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Genre> genres = new ArrayList<>();

        JSONObject movieDetailsJson = new JSONObject(jsonResult);

        int id = movieDetailsJson.getInt(KEY_ID);
        String title = movieDetailsJson.getString(KEY_TITLE);
        double rating = movieDetailsJson.getDouble(KEY_RATING);
        String imageUrl = UrlBuilder.buildPosterImageUrl(movieDetailsJson.getString(KEY_POSTER_PATH));
        String overview = movieDetailsJson.getString(KEY_OVERVIEW);
        String releaseDate = movieDetailsJson.getString(KEY_RELEASE_DATE);
        int runtime = movieDetailsJson.getInt(KEY_RUNTIME);

        JSONArray arrayGenres = movieDetailsJson.getJSONArray(ARRAY_GENRES);

        for (int i = 0; i < arrayGenres.length(); i++) {
            JSONObject genre = arrayGenres.getJSONObject(i);
            genres.add(new Genre(genre.getString(KEY_GENRE_NAME)));
        }

        return new Movie(id, title, overview, imageUrl, releaseDate, runtime, rating, genres);
    }

    /**
     * Parses the JSON result of the genres GET-request and stores the data into new Genre objects.
     *
     * @param jsonResult the JSON response from the server.
     * @return An ArrayList of Genre objects.
     * @throws JSONException If the JSON data cannot be properly parsed.
     */
    public static ArrayList<Genre> parseGenresJson(String jsonResult) throws JSONException {
        Log.d(TAG, "Method called: parseGenresJson");

        ArrayList<Genre> genres = new ArrayList<>();

        JSONObject genresJson = new JSONObject(jsonResult);

        JSONArray arrayGenres = genresJson.getJSONArray(ARRAY_GENRES);

        // Iterate through the entire array of genres, retrieve the data, create new Genre
        // objects and store them into the ArrayList.
        for(int i = 0; i < arrayGenres.length(); i++) {
            // Retrieve the genre object from the array.
            JSONObject genre = arrayGenres.getJSONObject(i);

            // Retrieve the required data and store it into variables.
            int id = genre.getInt(KEY_ID);
            String name = genre.getString(KEY_GENRE_NAME);

            genres.add(new Genre(id, name));
        }

        return genres;
    }

    public static String parseRequestTokenJson(String jsonResult) throws JSONException {
        Log.d(TAG, "Method called: parseTokenJson");

        JSONObject response = new JSONObject(jsonResult);

        return response.getString(KEY_REQUEST_TOKEN);
    }
    
    public static String parseSessionId(String jsonResult) throws JSONException {
        Log.d(TAG, "Method called: parseSessionId");
        
        JSONObject response = new JSONObject(jsonResult);
        
        return response.getString(KEY_SESSION_ID);
    }

    public static String parseCreateList(String jsonResult) throws JSONException {
        Log.d(TAG, "Method called: parseCreateWatchedList");

        JSONObject response = new JSONObject(jsonResult);

        return response.getString(KEY_LIST_ID);
    }

    public static class GenresApiListener implements GenresGetRequest.GenresApiListener {
        /**
         * Fills our global ArrayList with the retrieved genres.
         *
         * @param genres The list of genres retrieved by our GenresGetRequest.
         */
        @Override
        public void handleGenresResult(ArrayList<Genre> genres) {
            Log.d(TAG, "Method called: handleGenresResult");

            mGenres.addAll(genres);
        }
    }
}
