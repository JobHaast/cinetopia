package nl.avans.cinetopia.data_access;

import android.net.Uri;
import android.util.Log;

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

    // The pages
    private final static String PARAM_PAGE = "page";

    //paths
    private final static String MOVIE_PATH = "movie";
    private final static String POPULAR_PATH = "popular";
    private final static String GENRE_PATH = "genre";
    private final static String LIST_PATH = "list";

    public static URL buildPopularMovieListUrl() {
        Log.d(TAG, "buildPopularMovieListUrl is aangeroepen");

        //The params are appended to the base string
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

        //The params are appended to the base string
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

    public static String buildPosterImageUrl(String path) {
        Log.d(TAG, "Method called: buildPosterImageUrl");

        return BASE_URL_IMAGE + path;
    }


}




