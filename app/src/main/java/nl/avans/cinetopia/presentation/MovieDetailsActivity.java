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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.MovieDetailsGetRequest;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class MovieDetailsActivity extends Fragment {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    // Global attributes.
    int mId;
    private String watchedListId;
    private String watchListId;
    Intent mIntent;
    TextView textViewTitle;
    TextView textViewOverview;
    TextView textViewReleaseDateAndRuntime;
    TextView textViewGenres;
    TextView textViewRating;
    ImageView imageViewPoster;
    ImageView imageViewTmdbLogo;
    StringBuilder mGenresString = new StringBuilder();

    private String sessionId;

    public MovieDetailsActivity(int id, String sessionId, String watchedListId, String watchListId) {
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
        if (itemFilter != null){
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
        imageViewTmdbLogo = view.findViewById(R.id.iv_tmdb_logo_details);

        return view;
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
            case R.id.moviedetails_menu_options:

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
            textViewReleaseDateAndRuntime.setText(movie.getReleaseDate().substring(0, 4) + " | " + movie.getRuntime() + " min");

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
            Picasso.get().load("https://www.themoviedb.org/assets/2/v4/logos/208x226-stacked-green-9484383bd9853615c113f020def5cbe27f6d08a84ff834f41371f223ebad4a3c.png")
                    .fit().centerInside().into(imageViewTmdbLogo);
        }
    }

    public void composeImplicitIntent() {
        Log.d(TAG, "composeImplicitInten aangeroepen");

        StringBuilder builder = new StringBuilder();
        builder.append(textViewTitle.getText().toString());
        builder.append("\n");
        builder.append(textViewOverview.getText().toString());
        builder.append("\n");
        builder.append(textViewReleaseDateAndRuntime.getText().toString());
        builder.append("\n");
        builder.append(textViewGenres.getText().toString());
        builder.append("\n");
        builder.append(textViewRating.getText().toString());

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "Film");
        intent.putExtra(Intent.EXTRA_TEXT, builder.toString());
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Send Movie"));
        }
    }
}
