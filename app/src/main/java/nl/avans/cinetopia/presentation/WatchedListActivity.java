package nl.avans.cinetopia.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import nl.avans.cinetopia.adapters.PopularMoviesRecyclerViewAdapter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.data_access.get_requests.WatchedListGetRequest;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.domain.Movie;

public class WatchedListActivity extends Fragment implements PopularMoviesRecyclerViewAdapter.OnItemClickListener{
    private static final String TAG = WatchedListActivity.class.getSimpleName();

    // RecyclerView attributes
    private String sessionId;
    private RecyclerView mRecyclerView;
    private PopularMoviesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    public WatchedListActivity(String sessionId){
        this.sessionId = sessionId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        retrieveLatestGenresFromApi();
        retrieveWatchedMoviesFromApi();

        // Obtain a handle to the object.
        mRecyclerView = view.findViewById(R.id.activity_main_recyclerView);
        // Use a linear layout manager.
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Connect the RecyclerView to the layout manager.
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter.
        mAdapter = new PopularMoviesRecyclerViewAdapter(getActivity(), mMovies);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void retrieveWatchedMoviesFromApi() {
        WatchedListGetRequest task = new WatchedListGetRequest(new WatchedListActivity.WatchedMovieApiListener());
        task.execute(UrlBuilder.buildWatchedListUrl());
    }

    private void retrieveLatestGenresFromApi() {
        GenresGetRequest task = new GenresGetRequest(new JsonUtils.GenresApiListener());
        task.execute(UrlBuilder.buildGenreUrl());
    }

    class WatchedMovieApiListener implements WatchedListGetRequest.WatchedListApiListener {
        @Override
        public void handleMovieResult(ArrayList<Movie> movies) {
            Log.d(TAG, "handleMovieResult called");

            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
            mMovies.addAll(movies);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MovieDetailsActivity(mMovies.get(position).getId(), sessionId))
                .addToBackStack(null).commit();
    }
}
