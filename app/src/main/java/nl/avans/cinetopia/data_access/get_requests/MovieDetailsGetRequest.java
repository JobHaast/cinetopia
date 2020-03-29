package nl.avans.cinetopia.data_access.get_requests;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;
import nl.avans.cinetopia.domain.Movie;

public class MovieDetailsGetRequest extends AsyncTask<URL, Void, Movie> {

    // Tag for logging.
    private static final String TAG = MovieDetailsGetRequest.class.getSimpleName();

    // Listener attribute.
    private MovieDetailsApiListener mListener;

    // Constructor.
    public MovieDetailsGetRequest(MovieDetailsApiListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Movie doInBackground(URL... urls) {
        Log.d(TAG, "Method called: doInBackground");

        Movie movie = null;
        URL url = urls[0];

        String jsonResult;
        try {
            jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
            movie = JsonUtils.parseMovieDetailsJson(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);
        Log.d(TAG, "Method called: onPostExecute");

        // Pass the Movie to our listener.
        mListener.handleMovieDetails(movie);
    }

    public interface MovieDetailsApiListener {
        void handleMovieDetails(Movie movie);
    }
}
