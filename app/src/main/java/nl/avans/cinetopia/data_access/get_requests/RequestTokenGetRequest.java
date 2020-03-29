package nl.avans.cinetopia.data_access.get_requests;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;

public class RequestTokenGetRequest extends AsyncTask<URL, Void, String> {
    // Tag for logging.
    private static final String TAG = RequestTokenGetRequest.class.getSimpleName();

    private AsyncResponse asyncResponse;

    public RequestTokenGetRequest(AsyncResponse asyncResponse){
        this.asyncResponse = asyncResponse;
    }


    @Override
    protected String doInBackground(URL... urls) {
        Log.d(TAG, "Method called: doInBackground");

        String token = null;
        URL url = urls[0];

        String jsonResult;
        try {
            jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
            token = JsonUtils.parseRequestTokenJson(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        asyncResponse.processFinish(s);
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }
}
