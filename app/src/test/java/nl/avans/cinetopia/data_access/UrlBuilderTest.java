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
        String result = UrlBuilder.buildGenreUrl().toString();
        assertEquals(result, "https://api.themoviedb.org/3/genre/movie/list?api_key=4c422ac80f2c83f42b8f905d4303959d&language=en");
    }

    @Test
    public void buildSearchUrlTest() {
        String result = UrlBuilder.buildSearchUrl("John Wick").toString();
        assertEquals(result, "https://api.themoviedb.org/3/search/movie?api_key=4c422ac80f2c83f42b8f905d4303959d&query=John%20Wick&page=1&include_adult=false");
    }

    @Test
    public void buildMovieDetailsUrlTest() {
        String result = UrlBuilder.buildMovieDetailsUrl(245891).toString();
        assertEquals(result, "https://api.themoviedb.org/3/movie/245891?api_key=4c422ac80f2c83f42b8f905d4303959d&language=en");
    }

    @Test
    public void buildPosterImageUrlTest() {
        String result = UrlBuilder.buildPosterImageUrl("/5vHssUeVe25bMrof1HyaPyWgaP.jpg");
        assertEquals(result, "https://image.tmdb.org/t/p/w500/5vHssUeVe25bMrof1HyaPyWgaP.jpg");
    }

    @Test
    public void buildTopRatedMovieListUrlTest() {
        String result = UrlBuilder.buildTopRatedMovieListUrl().toString();
        assertEquals(result, "https://api.themoviedb.org/3/movie/top_rated?api_key=4c422ac80f2c83f42b8f905d4303959d&language=en&page=1");
    }

    @Test
    public void buildRequestTokenUrlTest() {
        String result = UrlBuilder.buildRequestTokenUrl().toString();
        assertEquals(result, "https://api.themoviedb.org/3/authentication/token/new?api_key=4c422ac80f2c83f42b8f905d4303959d");
    }

    @Test
    public void buildRequestTokenAuthorizationUrlTest() {
        String result = UrlBuilder.buildRequestTokenAuthorizationUrl("1");
        assertEquals(result, "https://www.themoviedb.org/authenticate/1");
    }

    @Test
    public void buildSessionPostRequestUrlTest() {
        String result = UrlBuilder.buildSessionPostRequestUrl("1").toString();
        assertEquals(result, "Request{method=POST, url=https://api.themoviedb.org/3/authentication/session/new?api_key=4c422ac80f2c83f42b8f905d4303959d, tag=null}");
    }

    @Test
    public void createWatchedList() {
        String result = UrlBuilder.createWatchedList("1").toString();
        assertEquals(result, "Request{method=POST, url=https://api.themoviedb.org/3/list?api_key=4c422ac80f2c83f42b8f905d4303959d&session_id=1, tag=null}");
    }

    @Test
    public void createWatchList() {
        String result = UrlBuilder.createWatchlist("1").toString();
        assertEquals(result, "Request{method=POST, url=https://api.themoviedb.org/3/list?api_key=4c422ac80f2c83f42b8f905d4303959d&session_id=1, tag=null}");
    }

    @Test
    public void buildGetListUrl() {
        String result = UrlBuilder.buildGetListUrl("1").toString();
        assertEquals(result, "https://api.themoviedb.org/3/list/1?api_key=4c422ac80f2c83f42b8f905d4303959d&language=en");
    }

    @Test
    public void buildAddMovieUrl() {
        String result = UrlBuilder.buildAddMovieUrl(1, "1", "1").toString();
        assertEquals(result, "Request{method=POST, url=https://api.themoviedb.org/3/list/1/add_item?api_key=4c422ac80f2c83f42b8f905d4303959d&session_id=1, tag=null}");
    }

    @Test
    public void buildRemoveMovieUrl() {
        String result = UrlBuilder.buildRemoveMovieUrl(1, "1", "1").toString();
        assertEquals(result, "Request{method=POST, url=https://api.themoviedb.org/3/list/1/remove_item?api_key=4c422ac80f2c83f42b8f905d4303959d&session_id=1, tag=null}");
    }

    @Test
    public void buildRatingPostUrl() {
        String result = UrlBuilder.buildRatingPostUrl(1.0, 1, "1").toString();
        assertEquals(result, "Request{method=POST, url=https://api.themoviedb.org/3/movie/1/rating?api_key=4c422ac80f2c83f42b8f905d4303959d&session_id=1, tag=null}");
    }
}