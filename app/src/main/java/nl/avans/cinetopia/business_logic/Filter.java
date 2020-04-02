package nl.avans.cinetopia.business_logic;

import android.util.Log;

import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class Filter {

    // Tag for logging.
    private final String TAG = Filter.class.getSimpleName();

    private ArrayList<Movie> mMovies;

    public Filter(ArrayList<Movie> movies) {
        this.mMovies = movies;
    }

    public ArrayList<Movie> filterRating(int ratingGroup) {
        switch (ratingGroup) {
            //If the ratingGroup is 1, the movies with ratings between 0 and 4 are loaded.
            case R.id.radio_button_1:
                Log.d(TAG, "filterRating: optie 1");
                return doFilterByRating(0, 4);

            //If the ratingGroup is 2, the movies with ratings between 4 and 6 are loaded.
            case R.id.radio_button_2:
                Log.d(TAG, "filterRating: optie 2");
                return doFilterByRating(4, 6);

            //If the ratingGroup is 3, the movies with ratings between 6 and 8 are loaded.
            case R.id.radio_button_3:
                Log.d(TAG, "filterRating: optie 3");
                return doFilterByRating(6, 8);

            //If the ratingGroup is 4, the movies with ratings between 8 and 10 are returned.
            case R.id.radio_button_4:
                Log.d(TAG, "filterRating: optie 4");
                return doFilterByRating(8, 10);

            //If none is selected, the complete list will be returned
            default:
                Log.d(TAG, "filterRating aangeroepen");
                return mMovies;
        }
    }

    public ArrayList<Movie> filterGenre(ArrayList<Integer> selectedGenreIds) {
        ArrayList<Movie> newMovieList = new ArrayList<>();
        Log.d(TAG, "filterGenre is aangeroepen");

        for (Movie movie : mMovies) {

            int counter = 0;

            for (int id : selectedGenreIds) {

                for (Genre genre : movie.getGenres()) {
                    if (genre.getId() == id) {
                        counter++;
                    }
                }

            }

            if (counter == selectedGenreIds.size()) {
                newMovieList.add(movie);
            }

        }

        return newMovieList;

    }

    public ArrayList<Movie> doFilterByRating(int min, int max) {
        ArrayList<Movie> newMovieList = new ArrayList<>();
        Log.d(TAG, "doFilterByRating aangeroepen movielistsize:" + mMovies.size());

        for (Movie movie : mMovies) {
            double rating = movie.getRating();
            if (rating >= min && rating <= max) {
                newMovieList.add(movie);
            }
        }

        Log.d(TAG, "doFilterByRating newMovieListSize: " + newMovieList.size());
        return newMovieList;
    }


}

