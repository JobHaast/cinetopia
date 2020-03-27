package nl.avans.cinetopia.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.data_access.UrlBuilder;
import nl.avans.cinetopia.data_access.get_requests.MovieDetailsGetRequest;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

import static android.nfc.NfcAdapter.EXTRA_ID;
import static nl.avans.cinetopia.presentation.MainActivity.EXTRA_GENRES;
import static nl.avans.cinetopia.presentation.MainActivity.EXTRA_IMAGE_URL;
import static nl.avans.cinetopia.presentation.MainActivity.EXTRA_OVERVIEW;
import static nl.avans.cinetopia.presentation.MainActivity.EXTRA_RATING;
import static nl.avans.cinetopia.presentation.MainActivity.EXTRA_RELEASE_DATE;
import static nl.avans.cinetopia.presentation.MainActivity.EXTRA_RUNTIME;
import static nl.avans.cinetopia.presentation.MainActivity.EXTRA_TITLE;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private androidx.appcompat.widget.Toolbar toolbar;

    // Global attributes.
    Intent mIntent;
    TextView textViewTitle;
    TextView textViewOverview;
    TextView textViewReleaseDateAndRuntime;
    TextView textViewGenres;
    TextView textViewRating;
    ImageView imageView;
    StringBuilder mGenresString = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIntent = getIntent();
        int id = Integer.parseInt(Objects.requireNonNull(mIntent.getStringExtra(EXTRA_ID)));

        retrieveMovieDetailsFromApi(id);

        textViewTitle = findViewById(R.id.tv_movie_detail_title);
        textViewOverview = findViewById(R.id.tv_movie_detail_overview);
        textViewReleaseDateAndRuntime = findViewById(R.id.tv_movie_detail_year_and_runtime);
        textViewGenres = findViewById(R.id.tv_movie_detail_genres);
        textViewRating = findViewById(R.id.tv_movie_details_rating);
        imageView = findViewById(R.id.iv_movie_detail_picture);
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
            textViewReleaseDateAndRuntime.setText(movie.getReleaseDate() + " - " + movie.getRuntime() + " min");

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
            Picasso.get().load(movie.getImageUrl()).fit().centerInside().into(imageView);

        }
    }
}
