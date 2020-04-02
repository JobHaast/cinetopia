package nl.avans.cinetopia.business_logic;

import org.junit.Test;

import java.util.ArrayList;

import nl.avans.cinetopia.domain.Genre;
import nl.avans.cinetopia.domain.Movie;

import static org.junit.Assert.*;

public class FilterTest {

    @Test
    public void filterGenreTestAction() {
        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Genre> genresContainingAction = new ArrayList<>();
        genresContainingAction.add(new Genre(28, "Action"));

        ArrayList<Genre> genresContainingThriller = new ArrayList<>();
        genresContainingThriller.add(new Genre(53, "thriller"));

        ArrayList<Genre> genresContainingActionAndThriller = new ArrayList<>();
        genresContainingActionAndThriller.add(new Genre(28, "Action"));
        genresContainingActionAndThriller.add(new Genre(53, "Thriller"));

        movies.add(new Movie(1, "John Wick - Action", "image/url", 7.2, genresContainingAction));
        movies.add(new Movie(2, "John Wick - Thriller", "image/url", 7.2, genresContainingThriller));
        movies.add(new Movie(3, "John Wick - Action & Thriller", "image/url", 7.2, genresContainingActionAndThriller));
        Filter filter = new Filter(movies);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(28);

        assertEquals(filter.filterGenre(ids).get(0).getTitle(), "John Wick - Action");
        assertEquals(filter.filterGenre(ids).get(1).getTitle(), "John Wick - Action & Thriller");
    }

    @Test
    public void filterGenreTestThriller() {
        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Genre> genresContainingAction = new ArrayList<>();
        genresContainingAction.add(new Genre(28, "Action"));

        ArrayList<Genre> genresContainingThriller = new ArrayList<>();
        genresContainingThriller.add(new Genre(53, "thriller"));

        ArrayList<Genre> genresContainingActionAndThriller = new ArrayList<>();
        genresContainingActionAndThriller.add(new Genre(28, "Action"));
        genresContainingActionAndThriller.add(new Genre(53, "Thriller"));

        movies.add(new Movie(1, "John Wick - Action", "image/url", 7.2, genresContainingAction));
        movies.add(new Movie(2, "John Wick - Thriller", "image/url", 7.2, genresContainingThriller));
        movies.add(new Movie(3, "John Wick - Action & Thriller", "image/url", 7.2, genresContainingActionAndThriller));

        Filter filter = new Filter(movies);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(53);

        assertEquals(filter.filterGenre(ids).get(0).getTitle(), "John Wick - Thriller");
        assertEquals(filter.filterGenre(ids).get(1).getTitle(), "John Wick - Action & Thriller");
    }

    @Test
    public void doFilterOnRatingsTestRatingLow() {
        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Genre> genresContainingAction = new ArrayList<>();
        genresContainingAction.add(new Genre(28, "Action"));

        ArrayList<Genre> genresContainingThriller = new ArrayList<>();
        genresContainingThriller.add(new Genre(53, "thriller"));

        ArrayList<Genre> genresContainingActionAndThriller = new ArrayList<>();
        genresContainingActionAndThriller.add(new Genre(28, "Action"));
        genresContainingActionAndThriller.add(new Genre(53, "Thriller"));

        movies.add(new Movie(1, "John Wick - 7.2", "image/url", 7.2, genresContainingAction));
        movies.add(new Movie(2, "John Wick - 10", "image/url", 10, genresContainingThriller));
        movies.add(new Movie(3, "John Wick - 4.5", "image/url", 4.5, genresContainingActionAndThriller));

        Filter filter = new Filter(movies);
        assertEquals(filter.doFilterByRating(4, 6).get(0).getTitle(), "John Wick - 4.5");
        assertNotEquals(filter.doFilterByRating(4, 6).get(0).getTitle(), "John Wick - 7.2");
        assertNotEquals(filter.doFilterByRating(4, 6).get(0).getTitle(), "John Wick - 10");
    }

    @Test
    public void doFilterOnRatingsTestRatingHigh() {
        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Genre> genresContainingAction = new ArrayList<>();
        genresContainingAction.add(new Genre(28, "Action"));

        ArrayList<Genre> genresContainingThriller = new ArrayList<>();
        genresContainingThriller.add(new Genre(53, "thriller"));

        ArrayList<Genre> genresContainingActionAndThriller = new ArrayList<>();
        genresContainingActionAndThriller.add(new Genre(28, "Action"));
        genresContainingActionAndThriller.add(new Genre(53, "Thriller"));

        movies.add(new Movie(1, "John Wick - 7.2", "image/url", 7.2, genresContainingAction));
        movies.add(new Movie(2, "John Wick - 10", "image/url", 10, genresContainingThriller));
        movies.add(new Movie(3, "John Wick - 4.5", "image/url", 4.5, genresContainingActionAndThriller));

        Filter filter = new Filter(movies);
        assertEquals(filter.doFilterByRating(8, 10).get(0).getTitle(), "John Wick - 10");
        assertNotEquals(filter.doFilterByRating(8, 10).get(0).getTitle(), "John Wick - 7.2");
        assertNotEquals(filter.doFilterByRating(8, 10).get(0).getTitle(), "John Wick - 4.5");
    }
}