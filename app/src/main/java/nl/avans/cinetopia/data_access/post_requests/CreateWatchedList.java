package nl.avans.cinetopia.data_access.post_requests;

import android.os.AsyncTask;
import android.util.Log;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;
import okhttp3.Request;

public class CreateWatchedList extends AsyncTask<Request, Void, String> {
    // Tag for logging.
    private static final String TAG = CreateWatchedList.class.getSimpleName();

    // Listener attribute.
    private AsyncResponseCreateWatchedList asyncResponse;

    // Constructor.
    public CreateWatchedList(AsyncResponseCreateWatchedList response) {
        this.asyncResponse = response;
    }


    @Override
    protected String doInBackground(Request... requests) {
        Log.d(TAG, "Method called: doInBackground in CreateWatchedList");

        Request request = requests[0];
        String list_id = "";

        String jsonResult;
        try {
            jsonResult = NetworkUtils.getResponseFromHttpUrlPost(request);
            list_id = JsonUtils.parseCreateList(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list_id;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        asyncResponse.processFinish(s);
    }

    public interface AsyncResponseCreateWatchedList {
        void processFinish(String output);
    }

}