package nl.avans.cinetopia.presentation.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.adapters.MoviesRecyclerViewAdapter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.MovieSearchGetRequest;
import nl.avans.cinetopia.domain.Movie;
import nl.avans.cinetopia.presentation.activities.MainActivity;

public class SearchFragment extends Fragment implements MoviesRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MoviesRecyclerViewAdapter mAdapter;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    private String mSessionId;
    private String mWatchedListId;
    private String mWatchListId;

    public SearchFragment(String sessionId, String watchedListId, String watchListId) {
        this.mSessionId = sessionId;
        this.mWatchedListId = watchedListId;
        this.mWatchListId = watchListId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        setHasOptionsMenu(true);

        SearchView mSearchView = view.findViewById(R.id.movie_search_view);
        mSearchView.setIconified(false);

        // Obtain a handle to the object.
        // RecyclerView attributes
        RecyclerView mRecyclerView = view.findViewById(R.id.activity_search_recyclerView);
        // Use a linear layout manager.
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        // Connect the RecyclerView to the layout manager.
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter.
        mAdapter = new MoviesRecyclerViewAdapter(getActivity(), mMovies);
        // Connect the RecyclerView to the adapter.
        mRecyclerView.setAdapter(mAdapter);
        // Set OnItemClickListener.
        mAdapter.setOnItemClickListener(SearchFragment.this);

        /* Add a divider to the RecyclerView. */
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                retrieveSearchResultsFromApi(newText);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        MenuItem itemFilter = menu.findItem(R.id.action_filter);

        if (item != null) {
            item.setVisible(false);
        }

        if (itemFilter != null) {
            itemFilter.setVisible(false);
        }
    }

    private void retrieveSearchResultsFromApi(String query) {
        URL searchUrl = UrlBuilder.buildSearchUrl(query);
        MovieSearchGetRequest task = new MovieSearchGetRequest(new MovieSearchApiListener());
        task.execute(searchUrl);
    }

    class MovieSearchApiListener implements MovieSearchGetRequest.MovieSearchApiListener {
        @Override
        public void handleSearchResult(ArrayList<Movie> movies) {
            Log.d(TAG, "Method called: handleSearchResult");

            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
            mMovies.clear();
            mMovies.addAll(movies);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MovieDetailsFragment(mMovies.get(position).getId(), mSessionId, mWatchedListId, mWatchListId))
                .addToBackStack(null).commit();
    }
}
