package nl.avans.cinetopia.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
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
    int id;
    TextView textViewTitle;
    TextView textViewOverview;
    TextView textViewReleaseDateAndRuntime;
    TextView textViewGenres;
    TextView textViewRating;
    ImageView imageView;
    StringBuilder mGenresString = new StringBuilder();

    public MovieDetailsActivity(int id){
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_movie_details, container, false);

        retrieveMovieDetailsFromApi(id);

        textViewTitle = view.findViewById(R.id.tv_movie_detail_title);
        textViewOverview = view.findViewById(R.id.tv_movie_detail_overview);
        textViewReleaseDateAndRuntime = view.findViewById(R.id.tv_movie_detail_year_and_runtime);
        textViewGenres = view.findViewById(R.id.tv_movie_detail_genres);
        textViewRating = view.findViewById(R.id.tv_movie_details_rating);
        imageView = view.findViewById(R.id.iv_movie_detail_picture);

        return view;
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
