package nl.avans.cinetopia.business_logic;

import android.util.Log;

import java.util.ArrayList;

import nl.avans.cinetopia.R;
import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

public class Filter {
    private final String TAG = "Filter";
    private ArrayList<Genre> genres;
    private ArrayList<Movie> movies;

    public Filter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> filterRating(int ratingGroup) {
        switch (ratingGroup) {
            //If the ratingGroup is 1, the movies with ratings between 0 and 4 are loaded.
            case R.id.radio_button_1:
                Log.d(TAG, "filterRating: optie 1");
                return doFilterOnRatings(0, 4);

            //If the ratingGroup is 2, the movies with ratings between 4 and 6 are loaded.
            case R.id.radio_button_2:
                Log.d(TAG, "filterRating: optie 2");
                return doFilterOnRatings(4, 6);

            //If the ratingGroup is 3, the movies with ratings between 6 and 8 are loaded.
            case R.id.radio_button_3:
                Log.d(TAG, "filterRating: optie 3");
                return doFilterOnRatings(6, 8);

            //If the ratingGroup is 4, the movies with ratings between 8 and 10 are returned.
            case R.id.radio_button_4:
                Log.d(TAG, "filterRating: optie 4");
                return doFilterOnRatings(8,10);

            //If none is selected, the complete list will be returned
            default:
                Log.d(TAG, "filterRating aangeroepen");
                return movies;
        }
    }

    public void filterGenre(ArrayList<Integer> selectedGenreIds) {
        ArrayList<Movie> newMovieList = new ArrayList<>();
        Log.d(TAG, "filterGenre is aangeroepen");

//        for (Movie movie : movies) {
//
//            int counter = 0;
//
//            for (int id : selectedGenreIds) {
//
//                for (Genre genre : genres) {
//                    if (genre.getId() == id) {
//                        counter++;
//                    }
//                }
//
//            }
//
//            if (counter == selectedGenreIds.size()) {
//                newMovieList.add(movie);
//            }
//
//        }

    }

    public ArrayList<Movie> doFilterOnRatings(int min, int max) {
        ArrayList<Movie> newMovieList = new ArrayList<>();
        Log.d(TAG, "doFilterOnRatings aangeroepen movielistsize:" + movies.size());

        for (Movie movie : movies) {
            double rating = movie.getRating();
//            if (rating > min && rating < max) {
                newMovieList.add(movie);
//            }
        }

        Log.d(TAG, "doFilterOnRatings newMovieListSize: " + newMovieList.size());
        return newMovieList;
    }


}

