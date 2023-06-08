package com.example.movies.service;

import com.example.movies.dto.Movie;
import com.example.movies.receivedata.specific.OmdbapiReceiveDataService;
import com.example.movies.receivedata.specific.ThemoviedbReceiveDataService;
import com.example.movies.repository.MovieRepository;
import com.example.movies.repository.QueryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
//@Sql({"/schema.sql"})
public class MoviesServiceTest {

    @InjectMocks
    MoviesServiceImpl service;

    @Mock
    OmdbapiReceiveDataService omdbapiReceiveDataService;

    @Mock
    ThemoviedbReceiveDataService themoviedbReceiveDataService;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    QueryRepository queryRepository;

    @Test
    void getMovies_Found_API_omdbapi() throws JsonProcessingException {
        String json = "[{\"title\":\"Making 'The Matrix'\",\"year\":\"1999\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Matrix Reloaded\",\"year\":\"2003\",\"director\":[\"Lana Wachowski\",\"Lilly Wachowski\"]},{\"title\":\"The Matrix Revisited\",\"year\":\"2001\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Matrix\",\"year\":\"1999\",\"director\":[\"Lana Wachowski\",\"Lilly Wachowski\"]},{\"title\":\"The Matrix Resurrections\",\"year\":\"2021\",\"director\":[\"Lana Wachowski\"]},{\"title\":\"The Matrix Revolutions\",\"year\":\"2003\",\"director\":[\"Lana Wachowski\",\"Lilly Wachowski\"]},{\"title\":\"A Glitch in the Matrix\",\"year\":\"2021\",\"director\":[\"Rodney Ascher\"]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        Set movies = objectMapper.readValue(json, Set.class);

        String title = "The+Matrix";
        String apiName = "omdbapi";
        when(service.getMovies(title, apiName)).thenReturn(movies);

        Set<Movie> result = service.getMovies(title, apiName);
        Assertions.assertEquals(result.size(), movies.size());
    }

    @Test
    void getMovies_Found_API_themoviedb() throws JsonProcessingException {
        String json = "[{\"title\":\"The Matrix Reloaded Revisited\",\"year\":\"2004\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Roots of the Matrix\",\"year\":\"2004\",\"director\":[]},{\"title\":\"The Matrix Revisited\",\"year\":\"2001\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Matrix Resurrections\",\"year\":\"2021\",\"director\":[\"Lana Wachowski\"]},{\"title\":\"The Matrix Revolutions\",\"year\":\"2003\",\"director\":[\"Lana Wachowski\",\"Lilly Wachowski\"]},{\"title\":\"Return to Source: The Philosophy of The Matrix\",\"year\":\"2004\",\"director\":[\"Josh Oreck\"]},{\"title\":\"Making 'The Matrix'\",\"year\":\"1999\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Matrix Reloaded\",\"year\":\"2003\",\"director\":[\"Lana Wachowski\",\"Lilly Wachowski\"]},{\"title\":\"The Matrix Reloaded: Car Chase\",\"year\":\"2004\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Matrix Reloaded: Pre-Load\",\"year\":\"2003\",\"director\":[\"Josh Oreck\"]},{\"title\":\"Sex and the Matrix\",\"year\":\"2000\",\"director\":[\"Joel Gallen\"]},{\"title\":\"The Matrix\",\"year\":\"1999\",\"director\":[\"Lana Wachowski\",\"Lilly Wachowski\"]},{\"title\":\"The Matrix Revolutions Revisited\",\"year\":\"2004\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Matrix: What Is Bullet-Time?\",\"year\":\"1999\",\"director\":[\"Josh Oreck\"]},{\"title\":\"A Glitch in the Matrix\",\"year\":\"2021\",\"director\":[\"Rodney Ascher\"]},{\"title\":\"Making 'Enter the Matrix'\",\"year\":\"2003\",\"director\":[\"Josh Oreck\"]},{\"title\":\"The Matrix Recalibrated\",\"year\":\"2004\",\"director\":[\"Josh Oreck\"]},{\"title\":\"Delusions End: Breaking Free of the Matrix\",\"year\":\"2021\",\"director\":[\"Philip Gardiner\"]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        Set movies = objectMapper.readValue(json, Set.class);

        String title = "The+Matrix";
        String apiName = "themoviedb";
        when(service.getMovies(title, apiName)).thenReturn(movies);

        Set<Movie> result = service.getMovies(title, apiName);
        Assertions.assertEquals(result.size(), movies.size());
    }

}
