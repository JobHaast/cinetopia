package nl.avans.cinetopia.data_access.utilities;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    // Tag for logging.
    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * Performs a GET request on the API.
     *
     * @param url The URL object containing our API URL.
     * @return A string containing the full JSON response.
     * @throws IOException If the method is unable to perform the GET request.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d(TAG, "Method called: getResponseFromHttpUrl");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getResponseFromHttpUrlPost(Request request){
        try (Response response = new OkHttpClient().newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            assert response.body() != null;
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
