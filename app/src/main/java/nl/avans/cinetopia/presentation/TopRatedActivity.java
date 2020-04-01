package nl.avans.cinetopia.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.adapters.MoviesRecyclerViewAdapter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.data_access.get_requests.TopRatedMovieGetRequest;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.domain.Movie;

public class TopRatedActivity extends Fragment implements MoviesRecyclerViewAdapter.OnItemClickListener {
    private static final String TAG = TopRatedActivity.class.getSimpleName();

    // RecyclerView attributes
    private String sessionId;
    private String watchedListId;
    private String watchListId;
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    public TopRatedActivity(String sessionId, String watchedListId, String watchListId){
        this.sessionId = sessionId;
        this.watchedListId = watchedListId;
        this.watchListId = watchListId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_top_rated, container, false);

        //Call methods for retrieving top rated movies from API
        retrieveLatestGenresFromApi();
        retrieveTopRatedMoviesFromApi();

        // Obtain a handle to the object.
        mRecyclerView = view.findViewById(R.id.activity_top_rated_recyclerView);
        // Use a linear layout manager.
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Connect the RecyclerView to the layout manager.
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter.
        mAdapter = new MoviesRecyclerViewAdapter(getActivity(), mMovies);
        // Connect the RecyclerView to the adapter.
        mRecyclerView.setAdapter(mAdapter);
        // Set OnItemClickListener.
        mAdapter.setOnItemClickListener(this);

        /* Add a divider to the RecyclerView. */
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    private void retrieveLatestGenresFromApi() {
        GenresGetRequest task = new GenresGetRequest(new JsonUtils.GenresApiListener());
        task.execute(UrlBuilder.buildGenreUrl());
    }

    private void retrieveTopRatedMoviesFromApi() {
        TopRatedMovieGetRequest task = new TopRatedMovieGetRequest(new TopRatedActivity.TopRatedMovieApiListener());
        task.execute(UrlBuilder.buildTopRatedMovieListUrl());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    class TopRatedMovieApiListener implements TopRatedMovieGetRequest.TopRatedMovieApiListener {
        @Override
        public void handleMovieResult(ArrayList<Movie> movies) {
            Log.d(TAG, "handleMovieResult called");

            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
            mMovies.addAll(movies);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Responsible for passing the details of the clicked Movie to the MovieDetailsActivity
     * and then starting that activity.
     *
     * @param position The position of the clicked Movie.
     */
    @Override
    public void onItemClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MovieDetailsActivity(mMovies.get(position).getId(), sessionId, watchedListId, watchListId))
                .addToBackStack(null).commit();
    }
}
