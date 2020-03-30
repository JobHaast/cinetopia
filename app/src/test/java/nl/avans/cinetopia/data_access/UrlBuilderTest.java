package nl.avans.cinetopia.data_access;

import android.net.Uri;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class UrlBuilderTest {

    @Test
    public void buildPopularMovieListUrl() {
        String result = UrlBuilder.buildPopularMovieListUrl().toString();
        assertEquals(result, "https://api.themoviedb.org/3/movie/popular?api_key=4c422ac80f2c83f42b8f905d4303959d&language=en&page=1");
    }

    @Test
    public void buildGenreUrl() {
    }

    @Test
    public void buildSearchUrl() {
    }

    @Test
    public void buildMovieDetailsUrl() {
    }

    @Test
    public void buildPosterImageUrl() {
    }

    @Test
    public void buildTopRatedMovieListUrl() {
    }

    @Test
    public void buildRequestTokenUrl() {
    }

    @Test
    public void buildRequestTokenAuthorizationUrl() {
    }

    @Test
    public void buildSessionPostRequestUrl() {
    }

    @Test
    public void buildWatchedListUrl() {
    }
}