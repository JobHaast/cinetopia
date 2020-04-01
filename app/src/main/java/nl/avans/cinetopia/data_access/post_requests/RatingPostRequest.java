package nl.avans.cinetopia.data_access.post_requests;

import android.os.AsyncTask;
import android.util.Log;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;
import okhttp3.Request;

public class RatingPostRequest extends AsyncTask<Request, Void, Integer> {
    // Tag for logging.
    private static final String TAG = RatingPostRequest.class.getSimpleName();

    // Listener attribute.
    private AsyncResponse asyncResponse;

    // Constructor.
    public RatingPostRequest(AsyncResponse response) {
        this.asyncResponse = response;
    }


    @Override
    protected Integer doInBackground(Request... requests) {
        Log.d(TAG, "Method called: doInBackground in RatingPostRequest");

        Request request = requests[0];
        int response = 0;

        String jsonResult;
        try {
            jsonResult = NetworkUtils.getResponseFromHttpUrlPost(request);
            response = JsonUtils.parseAddRemoveMovieToListResponse(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);

        asyncResponse.processFinish(s);
    }

    public interface AsyncResponse {
        void processFinish(int output);
    }

}
