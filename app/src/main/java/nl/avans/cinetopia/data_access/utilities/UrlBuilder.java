package nl.avans.cinetopia.data_access.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UrlBuilder {

    //Declaration of the static attributes
    private static final String TAG = "UrlBuilder";
    final static String BASE_URL_COCKTAILS = "https://www.thecocktaildb.com/api/json/v1/1/search.php";
    final static String PARAM_FILTER = "f";
    final static String FILTER = "a";

    public static URL buildUrl() {
        Log.d(TAG, "buildUrl is aangeroepen");
        //The params are appended to the base string
        Uri builtUri = Uri.parse(BASE_URL_COCKTAILS).buildUpon()
                .appendQueryParameter(PARAM_FILTER, FILTER)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built url: " + url);

        //The URL object url is returned to CocktailApiTask
        return url;
    }


}




