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

    // Tag for logging.
    private final static String TAG = "UrlBuilder";

    // Declaration of the attributes.
    private final static String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final static String BASE_URL_TMDB_ATHENTICATION = "https://www.themoviedb.org/";
    private final static String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w500";
    private final static String BASE_URL_BACKDROP = "https://image.tmdb.org/t/p/w1280";

    // The API Key is final and static so there can't be any typos made.
    private final static String PARAM_API_KEY = "api_key";
    private final static String API_KEY = "4c422ac80f2c83f42b8f905d4303959d";

    // The default Language is set to English.
    private final static String PARAM_LANGUAGE = "language";

    private static String LANGUAGE = Locale.getDefault().getLanguage();

    // For whether to show adult movies in search results or not.
    private final static String PARAM_ADULT = "include_adult";
    private final static String FALSE = "false";

    // Query param for the search URL.
    private final static String PARAM_QUERY = "query";

    // The pages.
    private final static String PARAM_PAGE = "page";

    // Paths.
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
    private static final String ADD_ITEM = "add_item";
    private static final String REMOVE_ITEM = "remove_item";
    private static final String RATING_PATH = "rating";

    public static URL buildPopularMovieListUrl(int id) {
        Log.d(TAG, "Method called: buildPopularMovieListUrl");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(POPULAR_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, String.valueOf(id))
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

        // Paths and parameters are appended to the base URL.
        String urlBuilder = BASE_URL_TMDB +
                MOVIE_PATH + "/" +
                id;
        Uri builtUri = Uri.parse(urlBuilder).buildUpon()
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

        return BASE_URL_POSTER + path;
    }

    public static String buildBackdropImageUrl(String path) {
        Log.d(TAG, "Method called: buildBackdropImageUrl");
        Log.d(TAG, BASE_URL_BACKDROP + path);

        return BASE_URL_BACKDROP + path;
    }


    public static URL buildTopRatedMovieListUrl(int id) {
        Log.d(TAG, "buildTopRatedMovieListUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(TOP_RATED_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, String.valueOf(id))
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

        String json = "{" +
                "\"request_token\":\"" +
                token +
                "\"}";

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

        String json = "{" +
                "\"name\":\"Watched\"," +
                "\"description\":\"Watched\"," +
                "\"language\":\"" +
                LANGUAGE +
                "\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built createWatchedList: " + request.toString());

        return request;
    }

    public static Request createWatchlist(String sessionId) {
        Log.d(TAG, "createWatchedList called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(LIST_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SESSION_ID, sessionId)
                .build();

        String json = "{" +
                "\"name\":\"Watch\"," +
                "\"description\":\"Watch\"," +
                "\"language\":\"" +
                LANGUAGE +
                "\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built createWatchedList: " + request.toString());

        return request;
    }

    public static URL buildGetListUrl(String listId) {
        Log.d(TAG, "buildGetListUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(LIST_PATH)
                .appendPath(listId)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built buildGetListUrl: " + url);

        return url;
    }

    public static Request buildAddMovieUrl(int movieId, String sessionId, String listId) {
        Log.d(TAG, "buildAddMovieUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(LIST_PATH)
                .appendPath(listId)
                .appendPath(ADD_ITEM)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SESSION_ID, sessionId)
                .build();

        String json = "{" +
                "\"media_id\":\"" +
                movieId +
                "\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built buildAddMovieUrl: " + request.toString());

        return request;
    }

    public static Request buildRemoveMovieUrl(int movieId, String sessionId, String listId) {
        Log.d(TAG, "buildRemoveMovieUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(LIST_PATH)
                .appendPath(listId)
                .appendPath(REMOVE_ITEM)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SESSION_ID, sessionId)
                .build();

        String json = "{" +
                "\"media_id\":\"" +
                movieId +
                "\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built buildRemoveMovieUrl: " + request.toString());

        return request;
    }

    public static Request buildRatingPostUrl(double rating, int movieId, String sessionId) {
        Log.d(TAG, "buildRemoveMovieUrl called");

        // Paths and parameters are appended to the base URL.
        Uri builtUri = Uri.parse(BASE_URL_TMDB).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(String.valueOf(movieId))
                .appendPath(RATING_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SESSION_ID, sessionId)
                .build();

        String json = "{" +
                "\"value\":\"" +
                rating +
                "\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(builtUri.toString())
                .post(body)
                .build();

        Log.d(TAG, "Built buildRatingPostUrl: " + request.toString());

        return request;
    }
}




