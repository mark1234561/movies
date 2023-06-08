package com.example.movies.service;

import com.example.movies.dto.Movie;
import com.example.movies.receivedata.specific.OmdbapiReceiveDataService;
import com.example.movies.receivedata.specific.ThemoviedbReceiveDataService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
public class MoviesServiceImpl implements MoviesService {
    private final String[] apiNames = new String[]{"omdbapi", "themoviedb"};

    private ThemoviedbReceiveDataService themoviedbReceiveDataService;

    private OmdbapiReceiveDataService omdbapiReceiveDataService;

    @Override
    public Set<Movie> getMovies(String title, String apiName) {
        if (apiName == null || apiName.isEmpty() ||
                !Arrays.asList(apiNames).contains(apiName.toLowerCase())) {
            log.error("API name error: {}", apiName);
            throw new RuntimeException("API name error");
        }

        return switch (apiName.toLowerCase()) {
            case "omdbapi" -> omdbapiReceiveDataService.getMovies(title, apiName.toLowerCase());
            case "themoviedb" -> themoviedbReceiveDataService.getMovies(title, apiName.toLowerCase());
            default -> throw new RuntimeException("API name error");

        };

    }
}
