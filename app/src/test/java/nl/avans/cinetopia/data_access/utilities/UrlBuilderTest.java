package nl.avans.cinetopia.data_access.utilities;

import static nl.avans.cinetopia.data_access.utilities.UrlBuilder.buildPopularMovieListUrl;
import static org.junit.jupiter.api.Assertions.*;

class UrlBuilderTest {

    @org.junit.jupiter.api.Test
    void buildPopularMovieListUrlTest() {
        String number = "3";
        String result = UrlBuilder.buildPopularMovieListUrl(number).toString();
        assertEquals(result, "test");
    }
}