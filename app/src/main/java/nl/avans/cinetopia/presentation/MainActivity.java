package nl.avans.cinetopia.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.business_logic.Filter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.RequestTokenGetRequest;
import nl.avans.cinetopia.data_access.post_requests.CreateSessionPostRequest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

    private String token;
    private String sessionId;

    public MainActivity() {
    }

    public MainActivity(String sessionId){
        this.sessionId = sessionId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//            setUpSession();
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new MainActivityFragment(sessionId)).commit();
            nvDrawer.setCheckedItem(R.id.nav_popular);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sessionId == null && token != null){
            retrieveSessionId();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_filter_menu, menu);
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
//                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new SearchActivity(sessionId)).addToBackStack(null).commit();
                break;
            case R.id.action_filter_rating:
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                final View dialog_layout = getLayoutInflater().inflate(R.layout.filter_layout, null);

                alertDialog.setView(dialog_layout);
                alertDialog.show();

                Button cancelButton = dialog_layout.findViewById(R.id.date_alertdialog_cancel);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick voor cancelbutton aangeroepen");
                        alertDialog.cancel();
                    }
                });

                break;


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
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new MainActivityFragment(sessionId)).addToBackStack(null).commit();
                break;
            case R.id.nav_top_rated:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new TopRatedActivity(sessionId)).addToBackStack(null).commit();
                break;
            case R.id.nav_to_be_watched:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new WatchlistListActivity(sessionId)).addToBackStack(null).commit();
                break;
            case R.id.nav_watched:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new WatchedListActivity(sessionId)).addToBackStack(null).commit();
                Log.d(TAG, "SessionId: " +sessionId);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout, new SettingsActivity(sessionId)).addToBackStack(null).commit();
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
//        outState.putSerializable(LIFECYCLE_CALLBACKS_TEXT_KEY, mMovies);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState called");

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
    public void openWebPage(String url) {
        Log.d(TAG, "openWebPage called "+ url +"");
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    class AsyncResponse implements RequestTokenGetRequest.AsyncResponse {

        @Override
        public void processFinish(String output) {
            token = output;
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
            sessionId = output;
        }
    }

    private void retrieveSessionId(){
        CreateSessionPostRequest task = new CreateSessionPostRequest(new AsyncResponseSessionId());
        task.execute(UrlBuilder.buildSessionPostRequestUrl(token));
    }
}
