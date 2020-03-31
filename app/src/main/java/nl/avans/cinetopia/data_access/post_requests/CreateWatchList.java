package nl.avans.cinetopia.data_access.post_requests;

import android.os.AsyncTask;
import android.util.Log;

import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.NetworkUtils;
import okhttp3.Request;

public class CreateWatchList extends AsyncTask<Request, Void, String> {
    // Tag for logging.
    private static final String TAG = CreateWatchList.class.getSimpleName();

    // Listener attribute.
    private AsyncResponseCreateWatchList asyncResponse;

    // Constructor.
    public CreateWatchList(AsyncResponseCreateWatchList response) {
        this.asyncResponse = response;
    }


    @Override
    protected String doInBackground(Request... requests) {
        Log.d(TAG, "Method called: doInBackground in CreateWatchList");

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

    public interface AsyncResponseCreateWatchList {
        void processFinish(String output);
    }

}
