package com.example.movies.receivedata.specific;

import com.example.movies.dto.Movie;
import com.example.movies.receivedata.ReceiveDataService;
import com.example.movies.repository.MovieRepository;
import com.example.movies.repository.QueryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class OmdbapiReceiveDataService extends ReceiveDataService {

    @Value("${apis.omdbapi.url}")
    private String API_DOMAIN;

    @Value("${apis.omdbapi.key}")
    private String API_KEY;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    QueryRepository queryRepository;

    protected Set<Movie> getMoviesFromApi(String title) {
        Set<Movie> movies = new HashSet<>();
        String uri = API_DOMAIN + "/?s=" + title + "&apikey=" + API_KEY;
        try {
            JsonNode node = get(new URL(uri));
            if (node.hasNonNull("Search")) {
                ArrayNode nodes = (ArrayNode) node.get("Search");
                nodes.forEach(n -> {
                    if (n.get("Type").asText().equals("movie")) {
                        Movie movie = receiveDetails(n.get("Title").asText(), n.get("Year").asText());
                        movies.add(movie);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    private Movie receiveDetails(String title, String year) {
        String uri = API_DOMAIN + "/?t=" + title + "&apikey=" + API_KEY;
        try {
            JsonNode node = get(new URL(uri));
            String directorString = node.get("Director").asText();
            Set<String> directors = new HashSet<>();
            if (directorString != null && !directorString.equals("")) {
                directors = new HashSet<>(Arrays.asList(directorString.trim().split("\\s*,\\s*")));
            }
            return Movie.builder().title(title).year(year).director(directors).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
