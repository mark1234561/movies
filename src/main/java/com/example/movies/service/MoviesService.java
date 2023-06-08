package com.example.movies.service;

import com.example.movies.dto.Movie;

import java.util.Set;

public interface MoviesService {
     Set<Movie> getMovies(String title, String apiName);
}
