package com.example.movies.controller;

import com.example.movies.dto.Movie;
import com.example.movies.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Controller
@RestController("/")
public class MoviesController {

    @Autowired
    MoviesService moviesService;

    @GetMapping(value = "/movies/{title}/{apiName}")
    @Cacheable(value = "movies", key = "#title.concat('-').concat(#apiName)")
    public Set<Movie> getData(@PathVariable String title, @PathVariable String apiName) {
        return moviesService.getMovies(title, apiName);
    }
}
