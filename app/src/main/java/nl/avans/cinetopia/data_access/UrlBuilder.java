package nl.avans.cinetopia.data_access;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {

    // Declaration of the attributes
    private final static String TAG = "UrlBuilder";
    private final static String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    // The API Key is final and static so there can't be any typos made
    private final static String PARAM_API_KEY = "api_key";
    private final static String API_KEY = "4c422ac80f2c83f42b8f905d4303959d";

    // The default Language is set to English
    private final static String PARAM_LANGUAGE = "language";
    private final static String LANGUAGE_ENGLISH = "en-US";

    // For whether to show adult movies in search results or not.
    private final static String PARAM_ADULT = "include_adult";
    private final static String TRUE = "true";
    private final static String FALSE = "false";

    // Query param for the search URL.
    private final static String PARAM_QUERY = "query";

    // The pages
    private final static String PARAM_PAGE = "page";

    // Paths
    private final static String SEARCH_PATH = "search";
    private final static String MOVIE_PATH = "movie";
    private final static String POPULAR_PATH = "popular";
    private final static String GENRE_PATH = "genre";
    private final static String LIST_PATH = "list";
    private final static String TOP_RATED_PATH = "top_rated";

    public static URL buildPopularMovieListUrl() {
        Log.d(TAG, "buildPopularMovieListUrl is aangeroepen");

        // The params are appended to the base string
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(POPULAR_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE_ENGLISH)
                .appendQueryParameter(PARAM_PAGE, "1")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built buildPopularMovieListUrl: " + url);

        return url;
    }

    public static URL buildGenreUrl() {
        Log.d(TAG, "Method called: buildGenreUrl");

        // The params are appended to the base string
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(GENRE_PATH)
                .appendPath(MOVIE_PATH)
                .appendPath(LIST_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE_ENGLISH)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built GenreUrl: " + url);

        return url;
    }

    public static URL buildSearchUrl(String query) {
        Log.d(TAG, "Method called: buildSearchUrl");

        // The params are appended to the base string.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(SEARCH_PATH)
                .appendPath(MOVIE_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_QUERY, query)
                .appendQueryParameter(PARAM_PAGE, "1")
                .appendQueryParameter(PARAM_ADULT, FALSE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built SearchUrl: " + url);

        return url;
    }

    public static URL buildMovieDetailsUrl(int id) {
        Log.d(TAG, "Method called: buildMovieDetailsUrl");

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(BASE_URL_TMDB)
                .append(MOVIE_PATH).append("/")
                .append(id);

        // The params are appended to the base string.
        Uri builtUri = Uri.parse(urlBuilder.toString()).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE_ENGLISH)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built MovieDetailsUrl: " + url);

        return url;
    }

    public static String buildPosterImageUrl(String path) {
        Log.d(TAG, "Method called: buildPosterImageUrl");

        return BASE_URL_IMAGE + path;
    }


    public static URL buildTopRatedMovieListUrl() {
        Log.d(TAG, "buildTopRatedMovieListUrl is aangeroepen");

        // The params are appended to the base string
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(TOP_RATED_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE_ENGLISH)
                .appendQueryParameter(PARAM_PAGE, "1")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built buildTopRatedMovieListUrl: " + url);

        return url;
    }


}




