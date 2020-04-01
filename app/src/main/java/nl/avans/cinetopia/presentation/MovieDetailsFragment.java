package nl.avans.cinetopia.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.MovieDetailsGetRequest;
import nl.avans.cinetopia.data_access.post_requests.AddMovieToList;
import nl.avans.cinetopia.data_access.post_requests.RatingPostRequest;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class MovieDetailsFragment extends Fragment {

    // Tag for logging.
    private static final String TAG = MovieDetailsFragment.class.getSimpleName();

    // Global attributes.
    private int mId;
    private String watchedListId;
    private String watchListId;
    private TextView textViewTitle;
    private TextView textViewOverview;
    private TextView textViewReleaseDateAndRuntime;
    private TextView textViewGenres;
    private TextView textViewRating;
    private ImageView imageViewPoster;
    private ImageView imageViewBackdrop;
    private ImageView imageViewTmdbLogo;
    private StringBuilder mGenresString = new StringBuilder();
    private RatingBar ratingBar;
    private Button submitButton;

    private String sessionId;

    MovieDetailsFragment(int id, String sessionId, String watchedListId, String watchListId) {
        this.mId = id;
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

        if (itemFilter != null) {
            itemFilter.setVisible(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_movie_details, container, false);

        retrieveMovieDetailsFromApi(mId);

        textViewTitle = view.findViewById(R.id.tv_movie_detail_title);
        textViewOverview = view.findViewById(R.id.tv_movie_detail_overview);
        textViewReleaseDateAndRuntime = view.findViewById(R.id.tv_movie_detail_year_and_runtime);
        textViewGenres = view.findViewById(R.id.tv_movie_detail_genres);
        textViewRating = view.findViewById(R.id.tv_movie_details_rating);
        imageViewPoster = view.findViewById(R.id.iv_movie_list_picture);
        imageViewBackdrop = view.findViewById(R.id.iv_movie_backdrop_picture);
        imageViewTmdbLogo = view.findViewById(R.id.iv_tmdb_logo_details);

        ratingBar = view.findViewById(R.id.rating_rating_bar);
        submitButton = view.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingPostRequest task = new RatingPostRequest(new AsyncResponsePostRating());
                task.execute(UrlBuilder.buildRatingPostUrl(ratingBar.getProgress(), mId, sessionId));
            }
        });

        return view;
    }

    class AsyncResponsePostRating implements RatingPostRequest.AsyncResponse {

        @Override
        public void processFinish(int output) {
            switch (output) {
                case (1):
                    Toast.makeText(getActivity(), getString(R.string.rating_result_succes),
                            Toast.LENGTH_SHORT).show();
                    break;
                case (12):
                    Toast.makeText(getActivity(), getString(R.string.rating_result_rerate),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the fragment menu items.
        inflater.inflate(R.menu.moviedetails_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moviedetails_menu_share:
                composeImplicitIntent();
                break;
            case R.id.add_to_watched:
                addMovieToWatchedList();
                break;
            case R.id.add_to_watchlist:
                addMovieToWatchList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrieveMovieDetailsFromApi(int id) {
        MovieDetailsGetRequest task = new MovieDetailsGetRequest(new MovieDetailsApiListener());
        task.execute(UrlBuilder.buildMovieDetailsUrl(id));
    }

    class MovieDetailsApiListener implements MovieDetailsGetRequest.MovieDetailsApiListener {
        @Override
        public void handleMovieDetails(Movie movie) {
            textViewTitle.setText(movie.getTitle());
            textViewOverview.setText(movie.getOverview());
            textViewReleaseDateAndRuntime.setText(movie.getReleaseDate().substring(0, 4) + "  •  " + movie.getRuntime() + " min");

            ArrayList<Genre> genres = new ArrayList<>(movie.getGenres());

            for (int i = 0; i < genres.size(); i++) {
                String name = genres.get(i).getName();

                if (i < genres.size() - 1) {
                    mGenresString.append(name).append(", ");
                } else {
                    mGenresString.append(name);
                }
            }

            textViewGenres.setText(mGenresString.toString());
            textViewRating.setText(String.valueOf(movie.getRating()));
            Picasso.get().load(movie.getImageUrl()).fit().centerInside().into(imageViewPoster);
            Picasso.get().load(movie.getBackdropUrl()).fit().centerInside().into(imageViewBackdrop);
            Picasso.get().load("https://www.themoviedb.org/assets/2/v4/logos/208x226-stacked-green-9484383bd9853615c113f020def5cbe27f6d08a84ff834f41371f223ebad4a3c.png")
                    .fit().centerInside().into(imageViewTmdbLogo);
        }
    }

    private void composeImplicitIntent() {
        Log.d(TAG, "Method called: composeImplicitIntent");

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "Film");
        String builder = textViewTitle.getText().toString() +
                "\n" +
                textViewOverview.getText().toString() +
                "\n" +
                textViewReleaseDateAndRuntime.getText().toString() +
                "\n" +
                textViewGenres.getText().toString() +
                "\n" +
                textViewRating.getText().toString();

        intent.putExtra(Intent.EXTRA_TEXT, builder);

        if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Share this movie with friends:"));
        }
    }

    private void addMovieToWatchedList() {
        AddMovieToList task = new AddMovieToList(new AsyncResponseAdd());
        task.execute(UrlBuilder.buildAddMovieUrl(mId, sessionId, watchedListId));
    }

    private void addMovieToWatchList() {
        AddMovieToList task = new AddMovieToList(new AsyncResponseAdd());
        task.execute(UrlBuilder.buildAddMovieUrl(mId, sessionId, watchListId));
    }

    class AsyncResponseAdd implements AddMovieToList.AsyncResponse {

        @Override
        public void processFinish(int output) {
            switch (output) {
                case (12):
                    Toast.makeText(getActivity(), getString(R.string.add_movie_result),
                            Toast.LENGTH_SHORT).show();
                    break;
                case (0):
                    Toast.makeText(getActivity(), getString(R.string.remove_movie_result_duplicate),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
