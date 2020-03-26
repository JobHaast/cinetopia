package nl.avans.cinetopia.data_access.utilities;

import nl.avans.cinetopia.data_access.UrlBuilder;

import static org.junit.jupiter.api.Assertions.*;

class UrlBuilderTest {

    @org.junit.jupiter.api.Test
    void buildPopularMovieListUrlTest() {
        String result = UrlBuilder.buildPopularMovieListUrl().toString();
        assertEquals(result, "test");
    }
}