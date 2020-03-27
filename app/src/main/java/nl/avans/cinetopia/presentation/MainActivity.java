package nl.avans.cinetopia.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.adapters.PopularMoviesRecyclerViewAdapter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.data_access.get_requests.MovieDetailsGetRequest;
import nl.avans.cinetopia.data_access.get_requests.PopularMovieGetRequest;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

    // Constant variables for MovieDetailsActivity.
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_OVERVIEW = "overview";
    public static final String EXTRA_IMAGE_URL = "imageUrl";
    public static final String EXTRA_RELEASE_DATE = "releaseDate";
    public static final String EXTRA_RUNTIME = "runtime";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_GENRES = "genres";

    // RecyclerView attributes
//    private RecyclerView mRecyclerView;
//    private PopularMoviesRecyclerViewAdapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Movie> mMovies = new ArrayList<>();
//    private Movie selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        UrlBuilder.buildMovieDetailsUrl(550);
//        retrieveLatestGenresFromApi();
//        retrievePopularMoviesFromApi();

        //set a toolbar to replace the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);

        // Takes care of the hamburger icon animation.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        // Find our drawer view and set it up
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        //Set first fragment first time
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new MainActivityFragment()).commit();
            nvDrawer.setCheckedItem(R.id.activity_main_recyclerView);
        }

//        // Obtain a handle to the object.
//        mRecyclerView = findViewById(R.id.activity_main_recyclerView);
//        // Use a linear layout manager.
//        mLayoutManager = new LinearLayoutManager(this);
//        // Connect the RecyclerView to the layout manager.
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // Specify an adapter.
//        mAdapter = new PopularMoviesRecyclerViewAdapter(this, mMovies);
//        // Connect the RecyclerView to the adapter.
//        mRecyclerView.setAdapter(mAdapter);
//        // Set OnItemClickListener.
//        mAdapter.setOnItemClickListener(MainActivity.this);
//
//        /* Add a divider to the RecyclerView. */
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
//        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_popular:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new MainActivityFragment()).commit();
                break;
            case R.id.nav_top_rated:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new TopRatedActivity()).commit();
                break;
            case R.id.nav_to_be_watched:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new WatchlistListActivity()).commit();
                break;
            case R.id.nav_watched:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new WatchedListActivity()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new SettingsActivity()).commit();
                break;
            default:

        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState called");

        super.onSaveInstanceState(outState);
        outState.putSerializable(LIFECYCLE_CALLBACKS_TEXT_KEY, mMovies);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState called");

        super.onRestoreInstanceState(savedInstanceState);
    }

//    private void retrievePopularMoviesFromApi() {
//        PopularMovieGetRequest task = new PopularMovieGetRequest(new PopularMovieApiListener());
//        task.execute(UrlBuilder.buildPopularMovieListUrl());
//    }
//
//    private void retrieveLatestGenresFromApi() {
//        GenresGetRequest task = new GenresGetRequest(new JsonUtils.GenresApiListener());
//        task.execute(UrlBuilder.buildGenreUrl());
//    }
//
//    /**
//     * Listener class for the PopularMovieGetRequest.
//     */
//    class PopularMovieApiListener implements PopularMovieGetRequest.PopularMovieApiListener {
//        /**
//         * Fills our global ArrayList with the retrieved movies and notifies the adapter that the
//         * dataset has changed.
//         *
//         * @param movies The list of movies retrieved by our PopularMovieGetRequest.
//         */
//        @Override
//        public void handleMovieResult(ArrayList<Movie> movies) {
//            Log.d(TAG, "Method called: handleMovieResult");
//
//            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
//            mMovies.addAll(movies);
//            mAdapter.notifyDataSetChanged();
//        }
//    }
//
//    /**
//     * Listener class for the MovieDetailsGetRequest.
//     */
//    class MovieDetailsApiListener implements MovieDetailsGetRequest.MovieDetailsApiListener {
//        /**
//         * Stores the returned movie details into our global Movie attribute.
//         *
//         * @param movie The movie object containing the movie's details.
//         */
//        @Override
//        public void handleMovieDetails(Movie movie) {
//            Log.d(TAG, "Method called: handleMovieDetails");
//
//            // Store the returned movie details into our global Movie attribute.
//            selectedMovie = movie;
//        }
//    }
//
//    /**
//     * Responsible for passing the details of the clicked Movie to the MovieDetailsActivity
//     * and then starting that activity.
//     *
//     * @param position The position of the clicked Movie.
//     */
//    @Override
//    public void onItemClick(int position) {
//
//        Intent detailsIntent = new Intent(this, MovieDetailsActivity.class);
//        Movie clickedMovie = mMovies.get(position);
//
//        MovieDetailsGetRequest task = new MovieDetailsGetRequest(new MovieDetailsApiListener());
//        task.execute(UrlBuilder.buildMovieDetailsUrl(clickedMovie.getId()));
//
//        ArrayList<Genre> genres;
//        genres = selectedMovie.getGenres();
//
//        detailsIntent.putExtra(EXTRA_TITLE, selectedMovie.getTitle());
//        detailsIntent.putExtra(EXTRA_OVERVIEW, selectedMovie.getOverview());
//        detailsIntent.putExtra(EXTRA_IMAGE_URL, selectedMovie.getImageUrl());
//        detailsIntent.putExtra(EXTRA_RELEASE_DATE, selectedMovie.getReleaseDate());
//        detailsIntent.putExtra(EXTRA_RUNTIME, selectedMovie.getRuntime());
//        detailsIntent.putExtra(EXTRA_RATING, selectedMovie.getRating());
//        detailsIntent.putParcelableArrayListExtra(EXTRA_GENRES, genres);
//    }
}
