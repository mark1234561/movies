package com.example.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages="com.example.movies.entity")
@EnableJpaRepositories
@EnableCaching
public class MoviesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }
}
