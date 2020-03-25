package nl.avans.cinetopia.data_access.utilities;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import nl.avans.cinetopia.domain.Movie;
import nl.avans.cinetopia.domain.Rating;

public class RequestPoster extends AsyncTask {
    private static final String TAG = RequestPoster.class.getSimpleName();
    private String feedback;

    public RequestPoster(){
        this.feedback = "";
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    private boolean postListWithUrl(URL url, ArrayList<Movie> movieList){
        return true;
    }

    private boolean postRatingWithUrl(URL url, Rating rating){
        return true;
    }

    private String getSessionId(URL url){
        return null;
    }

}
