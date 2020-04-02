package nl.avans.cinetopia.data_access.get_requests;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class GenresGetRequest extends AsyncTask<URL, Void, ArrayList<Genre>> {

    // Tag for logging.
    private static final String TAG = GenresGetRequest.class.getSimpleName();

    // Listener attribute.
    private GenresApiListener mListener;
    private GenresApiListener mListener2;

    // Constructor.
    public GenresGetRequest(GenresApiListener listener, GenresApiListener mListener2) {
        this.mListener = listener;
        this.mListener2 = mListener2;
    }

    public GenresGetRequest(GenresApiListener listener) {
        this(listener, null);
    }

    @Override
    protected ArrayList<Genre> doInBackground(URL... urls) {
        Log.d(TAG, "Method called: doInBackground");

        ArrayList<Genre> genres = new ArrayList<>();
        URL url = urls[0];

        String jsonResult;
        try {
            jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
            genres = JsonUtils.parseGenresJson(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }

    @Override
    protected void onPostExecute(ArrayList<Genre> genres) {
        super.onPostExecute(genres);
        Log.d(TAG, "Method called: onPostExecute");

        // Pass the ArrayList to our listener.
        mListener.handleGenresResult(genres);
        if(mListener2 != null){
            mListener2.handleGenresResult(genres);
        }

    }

    public interface GenresApiListener {
        void handleGenresResult(ArrayList<Genre> genres);
    }
}
