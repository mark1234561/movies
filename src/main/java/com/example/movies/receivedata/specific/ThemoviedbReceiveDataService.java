
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
import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class ThemoviedbReceiveDataService extends ReceiveDataService {

    @Value("${apis.themoviedb.url}")
    private String API_DOMAIN;

    @Value("${apis.themoviedb.key}")
    private String API_KEY;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    QueryRepository queryRepository;

    public Set<Movie> getMoviesFromApi(String title) {
        Set<Movie> movies = new HashSet<>();
        String uri = API_DOMAIN + "/search/movie?api_key=" + API_KEY + "&query=" + title + "&includeAdult=true";
        try {
            JsonNode node = get(new URL(uri));
            if (node.hasNonNull("results")) {
                ArrayNode nodes = (ArrayNode) node.get("results");
                nodes.forEach(n -> {
                    Movie movie = receiveDetails(n.get("id").asLong(), n.get("original_title").asText(),
                            n.get("release_date").asText().split("-")[0]
                    );
                    movies.add(movie);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    private Movie receiveDetails(Long id, String title, String year) {
        String uri = API_DOMAIN + "/movie/" + id + "/credits?api_key=" + API_KEY;
        try {
            JsonNode node = get(new URL(uri));
            Set<String> directors = new HashSet<>();
            if (node.hasNonNull("crew")) {
                ArrayNode nodes = (ArrayNode) node.get("crew");
                nodes.forEach(n -> {
                    if (n.hasNonNull("job") && n.get("job").asText().equals("Director")) {
                        directors.add(n.get("name").asText());
                    }
                });
            }
            return Movie.builder().title(title).year(year).director(directors).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
