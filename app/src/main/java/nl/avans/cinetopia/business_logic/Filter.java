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

    public Filter() {

    }

    public void filterRating(int ratingGroup) {
        //Option 1 id: 2131296490
        //Option 2 id: 2131296491
        //Option 3 id: 2131296492
        //Option 4 id: 2131296493

        switch (ratingGroup) {
            //If the ratingGroup is 1, the movies with ratings between 0 and 4 are loaded.
            case R.id.radio_button_1:
                Log.d(TAG, "filterRating: optie 1");

                break;

            //If the ratingGroup is 2, the movies with ratings between 4 and 6 are loaded.
            case R.id.radio_button_2:
                Log.d(TAG, "filterRating: optie 2");

                break;

            //If the ratingGroup is 3, the movies with ratings between 6 and 8 are loaded.
            case R.id.radio_button_3:
                Log.d(TAG, "filterRating: optie 3");

                break;
            //If the ratingGroup is 4, the movies with ratings between 8 and 10 are returned.
            case R.id.radio_button_4:
                Log.d(TAG, "filterRating: optie 4");

                break;

            //If none is selected, the complete list will be returned
            default:
                Log.d(TAG, "filterRating aangeroepen");
                break;
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


}

