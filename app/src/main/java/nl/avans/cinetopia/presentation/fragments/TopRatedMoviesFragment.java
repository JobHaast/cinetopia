package nl.avans.cinetopia.presentation.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.adapters.MoviesRecyclerViewAdapter;
import nl.avans.cinetopia.business_logic.Filter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.data_access.get_requests.TopRatedMovieGetRequest;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class TopRatedMoviesFragment extends Fragment implements MoviesRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = TopRatedMoviesFragment.class.getSimpleName();

    private MoviesRecyclerViewAdapter mAdapter;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private ArrayList<Movie> mMoviesBackup = new ArrayList<>();

    private String mSessionId;
    private String mWatchedListId;
    private String mWatchListId;

    private ArrayList<Genre> mTempGenres = new ArrayList<>();
    private ArrayList<Integer> mCheckedItems = new ArrayList<>();
    private boolean mBackup = false;

    public TopRatedMoviesFragment(String sessionId, String watchedListId, String watchListId) {
        this.mSessionId = sessionId;
        this.mWatchedListId = watchedListId;
        this.mWatchListId = watchListId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_top_rated, container, false);

        //This method call makes it possible to edit the menu buttons in this class
        setHasOptionsMenu(true);

        //Call methods for retrieving top rated movies from API
        retrieveLatestGenresFromApi();
        retrieveTopRatedMoviesFromApi();

        // Obtain a handle to the object.
        RecyclerView mRecyclerView = view.findViewById(R.id.activity_top_rated_recyclerView);
        // Use a linear layout manager.
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        // Connect the RecyclerView to the layout manager.
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter.
        mAdapter = new MoviesRecyclerViewAdapter(getActivity(), mMovies);
        // Connect the RecyclerView to the adapter.
        mRecyclerView.setAdapter(mAdapter);
        // Set OnItemClickListener.
        mAdapter.setOnItemClickListener(this);

        /* Add a divider to the RecyclerView. */
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    private void retrieveLatestGenresFromApi() {
        GenresGetRequest task = new GenresGetRequest(new JsonUtils.GenresApiListener(), new GenresApiListener());
        task.execute(UrlBuilder.buildGenreUrl());
    }

    class GenresApiListener implements GenresGetRequest.GenresApiListener {
        /**
         * Fills our global ArrayList with the retrieved genres.
         *
         * @param genres The list of genres retrieved by our GenresGetRequest.
         */
        @Override
        public void handleGenresResult(ArrayList<Genre> genres) {
            Log.d(TAG, "Method called: handleGenresResult");

            mTempGenres.clear();
            mTempGenres.addAll(genres);
        }
    }

    private void retrieveTopRatedMoviesFromApi() {
        TopRatedMovieGetRequest task = new TopRatedMovieGetRequest(new TopRatedMoviesFragment.TopRatedMovieApiListener());
        task.execute(UrlBuilder.buildTopRatedMovieListUrl(1), UrlBuilder.buildTopRatedMovieListUrl(2),
                UrlBuilder.buildTopRatedMovieListUrl(3), UrlBuilder.buildTopRatedMovieListUrl(4),
                UrlBuilder.buildTopRatedMovieListUrl(5));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_menu, menu);
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
            if (!mBackup) {
                mMoviesBackup.addAll(movies);
                mBackup = !mBackup;
            }
            mMovies.clear();
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
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MovieDetailsFragment(mMovies.get(position).getId(), mSessionId, mWatchedListId, mWatchListId))
                .addToBackStack(null).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_rating:
                Log.d(TAG, "onOptionsItemSelected in TopRatedMovieFragment aangeroepen op action_filter_rating");
                final AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity())).create();
                @SuppressLint("InflateParams") final View dialog_layout = getLayoutInflater().inflate(R.layout.filter_layout, null);

                alertDialog.setView(dialog_layout);
                alertDialog.show();

                Button cancelButton = dialog_layout.findViewById(R.id.rating_alertdialog_cancel);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick voor cancelbutton aangeroepen");
                        alertDialog.cancel();
                    }
                });

                final RadioGroup ratingGroup = dialog_layout.findViewById(R.id.rating_radioGroup);

                Button filterButton = dialog_layout.findViewById(R.id.rating_alertdialog_search);

                filterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int checkedRadioButtonId = ratingGroup.getCheckedRadioButtonId();
                        Log.d(TAG, "onClick :" + checkedRadioButtonId);
                        Filter filter = new Filter(mMoviesBackup);
                        TopRatedMoviesFragment.TopRatedMovieApiListener task = new TopRatedMoviesFragment.TopRatedMovieApiListener();
                        task.handleMovieResult(filter.filterRating(checkedRadioButtonId));
                        alertDialog.cancel();

                    }
                });
                break;

            case R.id.action_filter_genre:
                Log.d(TAG, "onOptionsItemSelected in TopRatedMovieFragment aangeroepen op action_filter_genre");
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                mBuilder.setTitle(R.string.filter_by_genre);
                String[] tempGenreNames = new String[mTempGenres.size()];
                for (int i = 0; i < mTempGenres.size(); i++) {
                    tempGenreNames[i] = mTempGenres.get(i).getName();
                }
                Log.d(TAG, "onOptionsItemSelected tempGenreNames length : " + tempGenreNames.length);
                boolean[] ifItemsCheckedBooleans = new boolean[tempGenreNames.length];
                mBuilder.setMultiChoiceItems(tempGenreNames, ifItemsCheckedBooleans, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        Log.d(TAG, "onClick aangeroepen op multipleChoiceButton:" + position);

                        if (isChecked) {
                            if (!mCheckedItems.contains(position)) {
                                mCheckedItems.add(position);
                            } else {
                                mCheckedItems.remove(position);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick aangeroepen op positiveButton");
                        Filter genreFilter = new Filter(mMoviesBackup);
                        ArrayList<Integer> checkedGenreIds = new ArrayList<>();
                        for (int i : mCheckedItems) {
                            checkedGenreIds.add(mTempGenres.get(i).getId());
                        }
                        TopRatedMoviesFragment.TopRatedMovieApiListener task = new TopRatedMoviesFragment.TopRatedMovieApiListener();
                        task.handleMovieResult(genreFilter.filterGenre(checkedGenreIds));
                        mCheckedItems.clear();
                        checkedGenreIds.clear();
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
