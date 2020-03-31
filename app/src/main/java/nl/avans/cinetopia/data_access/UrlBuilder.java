package nl.avans.cinetopia.data_access;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UrlBuilder {

    // Declaration of the attributes
    private final static String TAG = "UrlBuilder";
    private final static String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final static String BASE_URL_TMDB_ATHENTICATION = "https://www.themoviedb.org/";
    private final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    // The API Key is final and static so there can't be any typos made
    private final static String PARAM_API_KEY = "api_key";
    private final static String API_KEY = "4c422ac80f2c83f42b8f905d4303959d";

    // The default Language is set to English
    private final static String PARAM_LANGUAGE = "language";

    private static String LANGUAGE = Locale.getDefault().getLanguage();;

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
    private final static String TOKEN_PATH = "token";
    private final static String AUTHENTICATION_PATH = "authentication";
    private final static String AUTHENTICATE_PATH = "authenticate";
    private final static String SESSION_PATH = "session";
    private final static String NEW_PATH = "new";
    private static final String PARAM_SESSION_ID = "session_id";

    public static URL buildPopularMovieListUrl() {
        Log.d(TAG, "Method called: buildPopularMovieListUrl");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(POPULAR_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, "1")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built URL: " + url);

        return url;
    }

    public static URL buildGenreUrl() {
        Log.d(TAG, "Method called: buildGenreUrl");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(GENRE_PATH)
                .appendPath(MOVIE_PATH)
                .appendPath(LIST_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
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

        // Paths and parameters are appended to the base URL.
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

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(urlBuilder.toString()).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
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
        Log.d(TAG, "buildTopRatedMovieListUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(TOP_RATED_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
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

    public static URL buildRequestTokenUrl() {
        Log.d(TAG, "buildRequestTokenUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(AUTHENTICATION_PATH)
                .appendPath(TOKEN_PATH)
                .appendPath(NEW_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built buildRequestTokenUrl: " + url);

        return url;
    }

    public static String buildRequestTokenAuthorizationUrl(String token) {
        Log.d(TAG, "buildRequestTokenAuthorizationUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB_ATHENTICATION).buildUpon()
                .appendPath(AUTHENTICATE_PATH)
                .appendPath(token)
                .build();

        String url = builtUri.toString();

        Log.d(TAG, "Built buildRequestTokenAuthorizationUrl: " + url);

        return url;
    }

    public static Request buildSessionPostRequestUrl(String token) {
        Log.d(TAG, "buildSessionPostRequestUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(AUTHENTICATION_PATH)
                .appendPath(SESSION_PATH)
                .appendPath(NEW_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        String json = new StringBuilder()
                .append("{")
                .append("\"request_token\":\"")
                .append(token)
                .append("\"}").toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built buildSessionPostRequestUrl: " + request.toString());

        return request;
    }

    public static Request createWatchedList(String sessionId) {
        Log.d(TAG, "createWatchedList called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(LIST_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SESSION_ID, sessionId)
                .build();

        String json = new StringBuilder()
                .append("{")
                .append("\"name\":\"Watched\",")
                .append("\"description\":\"Watched\",")
                .append("\"language\":\"")
                .append(LANGUAGE)
                .append("\"}").toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built createWatchedList: " + request.toString());

        return request;
    }

    public static Request createWatchList(String sessionId) {
        Log.d(TAG, "createWatchedList called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(LIST_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SESSION_ID, sessionId)
                .build();

        String json = new StringBuilder()
                .append("{")
                .append("\"name\":\"Watch\",")
                .append("\"description\":\"Watch\",")
                .append("\"language\":\"")
                .append(LANGUAGE)
                .append("\"}").toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built createWatchedList: " + request.toString());

        return request;
    }

//    public static URL buildWatchedListUrl() {
//        Log.d(TAG, "buildWatchedListUrl called");
//
//        // Paths and parameters are appended to the base URL.
//        //TODO adapting method for buildWatchedListUrl
//        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
//                .appendPath(MOVIE_PATH)
//                .appendPath(TOP_RATED_PATH)
//                .appendQueryParameter(PARAM_API_KEY, API_KEY)
//                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
//                .appendQueryParameter(PARAM_PAGE, "1")
//                .build();
//
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        Log.d(TAG, "Built buildWatchedListUrl: " + url);
//
//        return url;
//    }
}




