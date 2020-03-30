package nl.avans.cinetopia.presentation;

import android.os.Bundle;
import android.os.Handler;
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
import nl.avans.cinetopia.adapters.PopularMoviesRecyclerViewAdapter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.data_access.get_requests.PopularMovieGetRequest;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.data_access.utilities.PaginationScrollListener;
import nl.avans.cinetopia.domain.Movie;

public class MainActivityFragment extends Fragment implements PopularMoviesRecyclerViewAdapter.OnItemClickListener {
    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentpage = PAGE_START;

    // RecyclerView attributes
    private RecyclerView mRecyclerView;
    private PopularMoviesRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);


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

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentpage += 1;

                // Mocking network delay for API call.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        retrievePopularMoviesFromApi(currentpage);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        retrieveLatestGenresFromApi();
        retrievePopularMoviesFromApi(1);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void retrievePopularMoviesFromApi(int page) {
        PopularMovieGetRequest task = new PopularMovieGetRequest(new PopularMovieApiListener());
        task.execute(UrlBuilder.buildPopularMovieListUrl(page));
    }

    private void retrieveLatestGenresFromApi() {
        GenresGetRequest task = new GenresGetRequest(new JsonUtils.GenresApiListener());
        task.execute(UrlBuilder.buildGenreUrl());
    }


    /**
     * Listener class for the PopularMovieGetRequest.
     */
    class PopularMovieApiListener implements PopularMovieGetRequest.PopularMovieApiListener {
        /**
         * Fills our global ArrayList with the retrieved movies and notifies the adapter that the
         * dataset has changed.
         *
         * @param movies The list of movies retrieved by our PopularMovieGetRequest.
         */
        @Override
        public void handleMovieResult(ArrayList<Movie> movies) {
            Log.d(TAG, "Method called: handleMovieResult");

            ArrayList<Movie> moviesResult = movies;

            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
            mMovies.addAll(movies);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MovieDetailsActivity(mMovies.get(position).getId()))
                .addToBackStack(null).commit();
    }
}