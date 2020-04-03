package nl.avans.cinetopia.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.RequestTokenGetRequest;
import nl.avans.cinetopia.data_access.post_requests.CreateSessionPostRequest;
import nl.avans.cinetopia.data_access.post_requests.CreateWatchList;
import nl.avans.cinetopia.data_access.post_requests.CreateWatchedList;
import nl.avans.cinetopia.presentation.fragments.PopularMoviesFragment;
import nl.avans.cinetopia.presentation.fragments.SearchFragment;
import nl.avans.cinetopia.presentation.fragments.TopRatedMoviesFragment;
import nl.avans.cinetopia.presentation.fragments.WatchedListFragment;
import nl.avans.cinetopia.presentation.fragments.WatchlistFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String MY_PREFERENCES = "myPreferences";
    private static final String SESSION_ID = "sessionId";
    private static final String WATCHED_LIST_ID = "watchedListId";
    private static final String WATCH_LIST_ID = "watchListId";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavDrawer;

    private SharedPreferences mPreferences;

    private String mToken;
    private String mSessionId;
    private String mWatchedListId;
    private String mWatchListId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set a toolbar to replace the actionbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawerLayout = findViewById(R.id.drawer_layout);

        // Takes care of the hamburger icon animation.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Find our drawer view and set it up
        mNavDrawer = findViewById(R.id.nvView);
        setupDrawerContent(mNavDrawer);

        //Shared preferences setup
        mPreferences = getApplicationContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);



        //Set first fragment first time
        if (savedInstanceState == null) {
            mSessionId = mPreferences.getString(SESSION_ID, null);
            mWatchedListId = mPreferences.getString(WATCHED_LIST_ID, null);
            mWatchListId = mPreferences.getString(WATCH_LIST_ID, null);

            if (mSessionId == null) {
                setUpSession();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new PopularMoviesFragment(mSessionId, mWatchedListId, mWatchListId)).addToBackStack(getString(R.string.popular)).commit();
            mNavDrawer.setCheckedItem(R.id.nav_popular);
            setTitle(getString(R.string.popular));

        }
    }

    class AsyncResponse implements RequestTokenGetRequest.AsyncResponse {

        @Override
        public void processFinish(String output) {
            mToken = output;
            openWebPage(UrlBuilder.buildRequestTokenAuthorizationUrl(output));
        }
    }

    private void setUpSession() {
        RequestTokenGetRequest task = new RequestTokenGetRequest(new AsyncResponse());
        task.execute(UrlBuilder.buildRequestTokenUrl());
    }

    class AsyncResponseSessionId implements CreateSessionPostRequest.AsyncResponse {

        @Override
        public void processFinish(String output) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(SESSION_ID, output);
            editor.apply();
            mSessionId = output;

            CreateWatchedList createWatchedList = new CreateWatchedList(new AsyncResponseWatchedList());
            createWatchedList.execute(UrlBuilder.createWatchedList(output));
            CreateWatchList createWatchList = new CreateWatchList(new AsyncResponseCreateWatchList());
            createWatchList.execute(UrlBuilder.createWatchlist(output));
        }
    }

    private void retrieveSessionId() {
        CreateSessionPostRequest task = new CreateSessionPostRequest(new AsyncResponseSessionId());
        task.execute(UrlBuilder.buildSessionPostRequestUrl(mToken));
    }

    class AsyncResponseWatchedList implements CreateWatchedList.AsyncResponseCreateWatchedList {

        @Override
        public void processFinish(String output) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(WATCHED_LIST_ID, output);
            editor.apply();
            mWatchedListId = output;
        }
    }

    class AsyncResponseCreateWatchList implements CreateWatchList.AsyncResponseCreateWatchList {

        @Override
        public void processFinish(String output) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(WATCH_LIST_ID, output);
            editor.apply();

            mWatchListId = output;
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new PopularMoviesFragment(mSessionId, mWatchedListId, mWatchListId)).addToBackStack(getString(R.string.popular)).commit();
            mNavDrawer.setCheckedItem(R.id.nav_popular);
            setTitle(getString(R.string.popular));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mSessionId == null) {
            retrieveSessionId();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new SearchFragment(mSessionId, mWatchedListId, mWatchListId)).addToBackStack(getString(R.string.search)).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_popular:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new PopularMoviesFragment(mSessionId, mWatchedListId, mWatchListId)).addToBackStack(getString(R.string.popular)).commit();
                break;
            case R.id.nav_top_rated:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new TopRatedMoviesFragment(mSessionId, mWatchedListId, mWatchListId)).addToBackStack(getString(R.string.top_rated)).commit();
                break;
            case R.id.nav_to_be_watched:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new WatchlistFragment(mSessionId, mWatchedListId, mWatchListId)).addToBackStack(getString(R.string.watchlist)).commit();
                break;
            case R.id.nav_watched:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new WatchedListFragment(mSessionId, mWatchedListId, mWatchListId)).addToBackStack(getString(R.string.watched)).commit();
                break;
            default:

        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState called");

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState called");

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount()-1 > 0) {
            Log.i("MainActivity", "popping backstack");
            fragmentManager.popBackStackImmediate();
            if(fragmentManager.getBackStackEntryCount()-1 != -1){
                String title = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getName();
                setTitle(title);
                if(title == getString(R.string.popular)){
                    mNavDrawer.setCheckedItem(R.id.nav_popular);
                }else if(title == getString(R.string.top_rated)){
                    mNavDrawer.setCheckedItem(R.id.nav_top_rated);
                }else if(title == getString(R.string.watchlist)){
                    mNavDrawer.setCheckedItem(R.id.nav_to_be_watched);
                }else if(title == getString(R.string.watched)){
                    mNavDrawer.setCheckedItem(R.id.nav_watched);
                }
            }
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    public void openWebPage(String url) {
        Log.d(TAG, "openWebPage called " + url + "");
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
