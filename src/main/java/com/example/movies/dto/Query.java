package com.example.movies.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Query {
    String query;
    String apiName;
}
