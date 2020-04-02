package nl.avans.cinetopia.data_access.get_requests;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;
import nl.avans.cinetopia.domain.Movie;

public class PopularMovieGetRequest extends AsyncTask<URL, Void, ArrayList<Movie>> {

    // Tag for logging.
    private static final String TAG = PopularMovieGetRequest.class.getSimpleName();

    // Listener attribute.
    private PopularMovieApiListener mListener;

    // Constructor.
    public PopularMovieGetRequest(PopularMovieApiListener listener) {
        this.mListener = listener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {
        Log.d(TAG, "Method called: doInBackground");

        ArrayList<Movie> movies = new ArrayList<>();

        String jsonResult;
        for (URL url : urls) {
            try {
                jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
                movies.addAll(JsonUtils.parseMoviesJson(jsonResult));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        Log.d(TAG, "Method called: onPostExecute");

        // Pass the ArrayList to our listener.
        mListener.handleMovieResult(movies);
    }

    public interface PopularMovieApiListener {
        void handleMovieResult(ArrayList<Movie> movies);
    }
}
