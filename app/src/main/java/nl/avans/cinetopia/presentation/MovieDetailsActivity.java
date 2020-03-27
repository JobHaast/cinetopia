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
import nl.avans.cinetopia.domain.Genre;

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
    String mTitle;
    String mOverview;
    String mImageUrl;
    String mReleaseDate;
    int mRuntime;
    double mRating;
    ArrayList<Genre> mGenres;
    StringBuilder mGenresString = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve the movie details we've passed from our MainActivity to this activity.
        mIntent = getIntent();
        mTitle = mIntent.getStringExtra(EXTRA_TITLE);
        mOverview = mIntent.getStringExtra(EXTRA_OVERVIEW);
        mImageUrl = mIntent.getStringExtra(EXTRA_IMAGE_URL);
        mReleaseDate = mIntent.getStringExtra(EXTRA_RELEASE_DATE);
        mRuntime = Integer.parseInt(Objects.requireNonNull(mIntent.getStringExtra(EXTRA_RUNTIME)));
        mRating = Double.parseDouble(Objects.requireNonNull(mIntent.getStringExtra(EXTRA_RATING)));
        mGenres = Objects.requireNonNull(mIntent.getExtras()).getParcelableArrayList(EXTRA_GENRES);

        TextView textViewTitle = findViewById(R.id.tv_movie_detail_title);
        TextView textViewOverview = findViewById(R.id.tv_movie_detail_overview);
        TextView textViewReleaseDateAndRuntime = findViewById(R.id.tv_movie_detail_year_and_runtime);
        TextView textViewGenres = findViewById(R.id.tv_movie_detail_genres);
        TextView textViewRating = findViewById(R.id.tv_movie_details_rating);
        ImageView imageView = findViewById(R.id.iv_movie_detail_picture);

        textViewTitle.setText(mTitle);
        textViewOverview.setText(mOverview);
        textViewReleaseDateAndRuntime.setText(mReleaseDate + " - " + mRuntime + " min");

        for (int i = 0; i < mGenres.size(); i++) {
            String name = mGenres.get(i).getName();

            if (i < mGenres.size() - 1) {
                mGenresString.append(name).append(", ");
            } else {
                mGenresString.append(name);
            }
        }

        textViewGenres.setText(mGenresString.toString());
        textViewRating.setText(String.valueOf(mRating));
        Picasso.get().load(mImageUrl).fit().centerInside().into(imageView);
    }
}
