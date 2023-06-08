package com.example.movies.receivedata.specific;

import com.example.movies.dto.Movie;
import com.example.movies.repository.MovieRepository;
import com.example.movies.repository.QueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@SpringBootTest
class ThemoviedbReceiveDataServiceTest {

    @InjectMocks
    ThemoviedbReceiveDataService service;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    QueryRepository queryRepository;

    @Value("${apis.themoviedb.url}")
    private String API_DOMAIN;

    @Value("${apis.themoviedb.key}")
    private String API_KEY;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "API_DOMAIN", API_DOMAIN);
        ReflectionTestUtils.setField(service, "API_KEY", API_KEY);
    }

    @Test
    void getMoviesFromApi_FoundAll() {
        String title = "The+Matrix";
        Set<Movie> result = service.getMoviesFromApi(title);
        notNull(result, "The value must be not null");
        result.forEach(r -> System.out.println(r.getTitle()));
        isTrue(result.size() == 20, "The value must be 20");
        isTrue(result.stream().anyMatch(r -> r.getTitle().equals("The Matrix")), "The » The Matrix « movie is contains.");
    }


    @Test
    void getMoviesFromApi_NotFound() {
        String title = "The+Mawwix";
        Set<Movie> result = service.getMoviesFromApi(title);
        notNull(result, "The value must be not null");
        isTrue(result.size() == 0, "The value must be 0");
    }


}
