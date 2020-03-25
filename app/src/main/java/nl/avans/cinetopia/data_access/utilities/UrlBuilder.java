package nl.avans.cinetopia.data_access.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {

    //Declaration of the attributes
    private static final String TAG = "UrlBuilder";
    final static String BASE_URL_THE_MOVIE_DATABASE = "https://api.themoviedb.org/3/";

    //The API Key is final and static so there can't be made any typos
    final static String PARAM_API_KEY = "api_key";
    final static String API_KEY = "4c422ac80f2c83f42b8f905d4303959d";

    //The default Language is set to English
    final static String PARAM_LANGUAGE = "language";
    final static String LANGUAGE_ENGLISH = "en-US";

    //The pages
    final static String PARAM_PAGE = "page";

    //Popular Movies List path
    final static String MOVIE_PATH = "movie";
    final static String POPULAR_LIST_PATH = "popular";


//  ExampleUrl: https://api.themoviedb.org/3/movie/popular?api_key=4c422ac80f2c83f42b8f905d4303959d&language=en-US&page=1
    public static URL buildPopularMovieListUrl(String page) {
        Log.d(TAG, "buildPopularMovieListUrl is aangeroepen");

        //If page is null or "null" it is given a page 1
        if (page == null || page == "null") {
            page = "1";
        }

        //The params are appended to the base string
        Uri builtUri = Uri.parse(BASE_URL_THE_MOVIE_DATABASE).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(POPULAR_LIST_PATH)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE_ENGLISH)
                .appendQueryParameter(PARAM_PAGE, page)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url: " + url);

        //The URL object url is returned to popularMovieListGetter
        return url;
    }


}




