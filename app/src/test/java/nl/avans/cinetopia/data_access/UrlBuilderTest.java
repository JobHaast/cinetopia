package nl.avans.cinetopia.data_access;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UrlBuilderTest {

    @Test
    public void buildPopularMovieListUrlTest() {
        String result = UrlBuilder.buildPopularMovieListUrl().toString();
        assertEquals(result, "https://api.themoviedb.org/3/movie/popular?api_key=4c422ac80f2c83f42b8f905d4303959d&language=en&page=1");
    }

    @Test
    public void buildGenreUrlTest() {
    }

    @Test
    public void buildSearchUrlTest() {
    }

    @Test
    public void buildMovieDetailsUrlTest() {
    }

    @Test
    public void buildPosterImageUrlTest() {
    }

    @Test
    public void buildTopRatedMovieListUrlTest() {
    }

    @Test
    public void buildRequestTokenUrlTest() {
    }

    @Test
    public void buildRequestTokenAuthorizationUrlTest() {
    }

    @Test
    public void buildSessionPostRequestUrlTest() {
    }

    @Test
    public void buildWatchedListUrlTest() {
    }
}