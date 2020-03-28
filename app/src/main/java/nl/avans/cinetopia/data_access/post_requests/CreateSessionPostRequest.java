package nl.avans.cinetopia.data_access.post_requests;

import android.os.AsyncTask;

import java.net.URL;

public class CreateSessionPostRequest extends AsyncTask<URL, Void, String> {

    // Tag for logging.
    private static final String TAG = CreateSessionPostRequest.class.getSimpleName();

    // Listener attribute.
    private CreateSessionApiListener mListener;

    // Constructor.
    public CreateSessionPostRequest(CreateSessionApiListener listener) {
        this.mListener = listener;
    }

    @Override
    protected String doInBackground(URL... urls) {
        return null;
    }

    @Override
    protected void onPostExecute(String sessionId) {
        super.onPostExecute(sessionId);
    }

    public interface CreateSessionApiListener {
        void handleSessionResult(String sessionId);
    }
}
