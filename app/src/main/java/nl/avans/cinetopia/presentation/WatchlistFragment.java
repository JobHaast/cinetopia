package nl.avans.cinetopia.presentation;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import nl.avans.cinetopia.R;
import nl.avans.cinetopia.adapters.MoviesRecyclerViewAdapter;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.GenresGetRequest;
import nl.avans.cinetopia.data_access.get_requests.ListGetRequest;
import nl.avans.cinetopia.data_access.post_requests.RemoveMovieFromList;
import nl.avans.cinetopia.data_access.utilities.JsonUtils;
import nl.avans.cinetopia.domain.Movie;

public class WatchlistFragment extends Fragment implements MoviesRecyclerViewAdapter.OnItemClickListener{
    private static final String TAG = WatchedListFragment.class.getSimpleName();

    private MoviesRecyclerViewAdapter mAdapter;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    private String sessionId;
    private String watchedListId;
    private String watchListId;

    WatchlistFragment(String sessionId, String watchedListId, String watchListId){
        this.sessionId = sessionId;
        this.watchedListId = watchedListId;
        this.watchListId = watchListId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Confirm this fragment has menu items.
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        MenuItem itemFilter = menu.findItem(R.id.action_filter);
        if (itemSearch != null) {
            itemSearch.setVisible(false);
        }
        if (itemFilter != null){
            itemFilter.setVisible(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        retrieveLatestGenresFromApi();
        retrieveWatchedMoviesFromApi();

        // Obtain a handle to the object.
        // RecyclerView attributes
        RecyclerView mRecyclerView = view.findViewById(R.id.activity_main_recyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the fragment menu items.
        inflater.inflate(R.menu.share_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            composeImplicitIntent();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void retrieveWatchedMoviesFromApi() {
        ListGetRequest task = new ListGetRequest(new MovieApiListener());
        task.execute(UrlBuilder.buildGetListUrl(watchListId));
    }

    private void retrieveLatestGenresFromApi() {
        GenresGetRequest task = new GenresGetRequest(new JsonUtils.GenresApiListener());
        task.execute(UrlBuilder.buildGenreUrl());
    }

    private void removeMovieFromWatchList(int id){
        RemoveMovieFromList task = new RemoveMovieFromList(new AsyncResponseRemove());
        task.execute(UrlBuilder.buildRemoveMovieUrl(id, sessionId, watchListId));
    }

    class MovieApiListener implements ListGetRequest.WatchedListApiListener {
        @Override
        public void handleMovieResult(ArrayList<Movie> movies) {
            Log.d(TAG, "handleMovieResult called");

            // Add all movies to our ArrayList and notify the adapter that the dataset has changed.
            mMovies.clear();
            mMovies.addAll(movies);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void composeImplicitIntent() {
        Log.d(TAG, "composeImplicitInten aangeroepen");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mMovies.size(); i++) {
            builder.append(mMovies.get(i).getTitle());
            if (i != mMovies.size()){
                builder.append("\n");
            }
        }

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "Film");
        intent.putExtra(Intent.EXTRA_TEXT, builder.toString());
        if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Send Watched List"));
        }
    }


    @Override
    public void onItemClick(int position) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frameLayout, new MovieDetailsFragment(mMovies.get(position).getId(), sessionId, watchedListId, watchListId))
                .addToBackStack(null).commit();
    }

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            if (direction == ItemTouchHelper.LEFT) {
                final int position = viewHolder.getAdapterPosition();
                int id = mMovies.get(position).getId();
                removeMovieFromWatchList(id);
                mMovies.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()), R.color.colorTextToolbar))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    class AsyncResponseRemove implements RemoveMovieFromList.AsyncResponse {

        @Override
        public void processFinish(int output) {
            switch (output){
                case (13):
                    Toast.makeText(getActivity(), getString(R.string.remove_movie_result),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
