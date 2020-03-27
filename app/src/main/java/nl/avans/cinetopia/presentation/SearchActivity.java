package nl.avans.cinetopia.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.SearchView;

import java.net.URL;
import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.adapters.MovieSearchRecyclerViewAdapter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.MovieSearchGetRequest;
import nl.avans.cinetopia.domain.Movie;

public class SearchActivity extends AppCompatActivity implements MovieSearchRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private androidx.appcompat.widget.Toolbar toolbar;

    private SearchView mSearchView;

    // RecyclerView attributes
    private RecyclerView mRecyclerView;
    private MovieSearchRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchView = findViewById(R.id.movie_search_view);

        // Obtain a handle to the object.
        mRecyclerView = findViewById(R.id.activity_search_recyclerView);
        // Use a linear layout manager.
        mLayoutManager = new LinearLayoutManager(this);
        // Connect the RecyclerView to the layout manager.
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter.
        mAdapter = new MovieSearchRecyclerViewAdapter(this, mMovies);
        // Connect the RecyclerView to the adapter.
        mRecyclerView.setAdapter(mAdapter);
        // Set OnItemClickListener.
        mAdapter.setOnItemClickListener(SearchActivity.this);

        /* Add a divider to the RecyclerView. */
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    retrieveSearchResultsFromApi(newText);
                }

                return true;
            }
        });
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

    }
}
