package com.example.movies.repository;

import com.example.movies.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findAllByTitleAndApiName(String title, String apiName);

    List<MovieEntity> findAllByTitleLikeIgnoreCaseAndApiName(String titleQuery, String apiName);
}
