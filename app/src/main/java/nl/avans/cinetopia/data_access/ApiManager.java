package nl.avans.cinetopia.data_access;

import java.util.ArrayList;

import nl.avans.cinetopia.data_access.get_requests.PopularMovieGetRequest;
import nl.avans.cinetopia.data_access.post_requests.RequestPoster;
import nl.avans.cinetopia.domain.Movie;

public class ApiManager{
    private static final String TAG = ApiManager.class.getSimpleName();

    private ArrayList<Movie> movies;
    private RequestPoster poster;
    private PopularMovieGetRequest getter;

}
