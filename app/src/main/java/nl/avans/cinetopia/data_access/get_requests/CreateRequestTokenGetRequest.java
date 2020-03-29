package nl.avans.cinetopia.data_access.get_requests;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;

public class CreateRequestTokenGetRequest extends AsyncTask<URL, Void, String> {

    // Tag for logging.
    private static final String TAG = CreateRequestTokenGetRequest.class.getSimpleName();

    // Listener attribute.
    private RequestTokenApiListener mListener;

    // Constructor.
    public CreateRequestTokenGetRequest(RequestTokenApiListener listener) {
        this.mListener = listener;
    }

    @Override
    protected String doInBackground(URL... urls) {
        Log.d(TAG, "Method called: doInBackground");

        String token = "";
        URL url = urls[0];

        String jsonResult;
        try {
            jsonResult = NetworkUtils.performGetRequest(url);
            token = JsonUtils.createRequestToken(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);
        Log.d(TAG, "Method called: onPostExecute");

        // Pass the string to our listener.
        mListener.handleRequestToken(token);
    }

    public interface RequestTokenApiListener {
        void handleRequestToken(String token);
    }
}
