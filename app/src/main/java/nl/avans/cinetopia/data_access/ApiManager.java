package nl.avans.cinetopia.data_access;

import android.os.AsyncTask;

import java.util.ArrayList;

import nl.avans.cinetopia.data_access.utilities.RequestGetter;
import nl.avans.cinetopia.data_access.utilities.RequestPoster;
import nl.avans.cinetopia.domain.Movie;

public class ApiManager{
    private static final String TAG = ApiManager.class.getSimpleName();

    private ArrayList<Movie> movies;
    private RequestPoster poster;
    private RequestGetter getter;

}
