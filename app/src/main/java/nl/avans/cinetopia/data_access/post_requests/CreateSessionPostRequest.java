package nl.avans.cinetopia.data_access.post_requests;

import android.os.AsyncTask;
import android.util.Log;

import nl.avans.cinetopia.data_access.get_requests.TopRatedMovieGetRequest;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;
import okhttp3.Request;

public class CreateSessionPostRequest extends AsyncTask<Request, Void, String> {
    // Tag for logging.
    private static final String TAG = TopRatedMovieGetRequest.class.getSimpleName();

    // Listener attribute.
    private AsyncResponse asyncResponse;

    // Constructor.
    public CreateSessionPostRequest(AsyncResponse response) {
        this.asyncResponse = response;
    }


    @Override
    protected String doInBackground(Request... requests) {
        Log.d(TAG, "Method called: doInBackground in TopRatedMovieGetRequest");

        Request request = requests[0];
        String sessionId = "";

        String jsonResult;
        try {
            jsonResult = NetworkUtils.getResponseFromHttpUrlPost(request);
            sessionId = JsonUtils.parseSessionId(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionId;
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
