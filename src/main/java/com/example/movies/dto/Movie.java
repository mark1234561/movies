package com.example.movies.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Movie {
    String title;
    String year;
    Set<String> director;
}
