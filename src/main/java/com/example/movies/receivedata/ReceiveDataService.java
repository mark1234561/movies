package com.example.movies.receivedata;

import com.example.movies.dto.Movie;
import com.example.movies.entity.MovieEntity;
import com.example.movies.entity.QueryEntity;
import com.example.movies.repository.MovieRepository;
import com.example.movies.repository.QueryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class ReceiveDataService {

    protected String API_NAME;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    QueryRepository queryRepository;

    public Set<Movie> getMovies(String titleQuery, String apiName) {
        API_NAME = apiName;
        Set<Movie> movies = new HashSet<>();
        if (!queryRepository.findAllByQueryAndApiName(titleQuery, API_NAME).isEmpty()) {
            log.info("receiving data from local DB » {} {}", API_NAME, titleQuery);
            Set<Movie> finalMovies = movies;
            movieRepository.findAllByTitleLikeIgnoreCaseAndApiName("%" + URLDecoder.decode(titleQuery, Charset.defaultCharset()) + "%", API_NAME)
                    .forEach(m -> finalMovies.add(Movie.builder().title(m.getTitle()).year(m.getYear()).director(m.getDirector()).build()));
        } else {
            log.info("receiving data from remote API »  {} {}", API_NAME, titleQuery);
            movies = getMoviesFromApi(titleQuery);
            saveMoviesToDB(titleQuery, movies);
        }
        return movies;
    }

    protected Set<Movie> getMoviesFromApi(String titleQuery) {
        throw new RuntimeException("This method is not implemented here.");
    }

    protected void saveMoviesToDB(String titleQuery, Set<Movie> movies) {
        queryRepository.save(QueryEntity.builder().query(titleQuery).apiName(API_NAME).build());
        movies.forEach(movie -> {
            if (movieRepository.findAllByTitleAndApiName(movie.getTitle(), API_NAME).isEmpty()) {
                movieRepository.save(MovieEntity.builder()
                        .title(movie.getTitle())
                        .year(movie.getYear())
                        .director(movie.getDirector())
                        .apiName(API_NAME)
                        .build());
            }
        });
    }

    protected static JsonNode get(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(url);
    }

}
