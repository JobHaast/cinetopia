package nl.avans.cinetopia.presentation;

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
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.adapters.MoviesRecyclerViewAdapter;
import nl.avans.cinetopia.business_logic.Filter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.data_access.get_requests.PopularMovieGetRequest;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class PopularMoviesFragment extends Fragment implements MoviesRecyclerViewAdapter.OnItemClickListener {
    private static final String TAG = PopularMoviesFragment.class.getSimpleName();

    // RecyclerView attributes
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private ArrayList<Movie> mMoviesBackUp = new ArrayList<>();
    private ProgressBar mProgressBar;

    private String sessionId;
    private String watchedListId;
    private String watchListId;

    private ArrayList<Genre> tempGenres = new ArrayList<>();
    private String[] tempGenreNames;
    private boolean[] ifItemsCheckedBooleans;
    private ArrayList<Integer> mCheckedItems = new ArrayList<>();
    private boolean backUp = false;


    public PopularMoviesFragment(String sessionId, String watchedListId, String watchListId) {
        this.sessionId = sessionId;
        this.watchedListId = watchedListId;
        this.watchListId = watchListId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        setHasOptionsMenu(true);

        retrieveLatestGenresFromApi();
        retrievePopularMoviesFromApi();

        // Obtain a handle to the objects.
        mRecyclerView = view.findViewById(R.id.activity_main_recyclerView);
        mProgressBar = view.findViewById(R.id.main_progress);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void retrievePopularMoviesFromApi() {
        PopularMovieGetRequest task = new PopularMovieGetRequest(new PopularMovieApiListener());
        task.execute(UrlBuilder.buildPopularMovieListUrl());
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

            tempGenres.clear();
            tempGenres.addAll(genres);
//            Log.d(TAG, "Hier moet je zijn: " + tempGenres.size());
        }
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

            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
            if(!backUp){
                mMoviesBackUp.addAll(movies);
                backUp = !backUp;
            }
            Log.d(TAG, "Hier moet je zijn: " + mMoviesBackUp.size());

            mMovies.clear();
            mMovies.addAll(movies);

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MovieDetailsFragment(mMovies.get(position).getId(), sessionId, watchedListId, watchListId))
                .addToBackStack(null).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_rating:
                Log.d(TAG, "onOptionsItemSelected in PopularMoviesFragment aangeroepen op action_filter_rating");
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                final View dialog_layout = getLayoutInflater().inflate(R.layout.filter_layout, null);

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
                        Filter filter = new Filter(mMoviesBackUp);
                        PopularMovieApiListener task = new PopularMovieApiListener();
                        task.handleMovieResult(filter.filterRating(checkedRadioButtonId));
                        alertDialog.cancel();

                    }
                });
                break;

            case R.id.action_filter_genre:
                Log.d(TAG, "onOptionsItemSelected in PopularMoviesFragment aangeroepen op action_filter_genre");
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle(R.string.filter_by_genre);
                tempGenreNames = new String[tempGenres.size()];
                for (int i = 0; i < tempGenres.size(); i++) {
                    tempGenreNames[i] = tempGenres.get(i).getName();
                }
                Log.d(TAG, "onOptionsItemSelected tempGenreNames length : " + tempGenreNames.length);
                ifItemsCheckedBooleans = new boolean[tempGenreNames.length];
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
                        Filter genreFilter = new Filter(mMoviesBackUp);
                        ArrayList<Integer> checkedGenreIds = new ArrayList<>();
                        for (int i : mCheckedItems) {
                            checkedGenreIds.add(tempGenres.get(i).getId());
                        }
                        PopularMovieApiListener task = new PopularMovieApiListener();
                        task.handleMovieResult(genreFilter.filterGenre(checkedGenreIds, tempGenres));
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




