package com.example.movies.repository;

import com.example.movies.entity.QueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QueryRepository extends JpaRepository<QueryEntity, Long> {
    List<QueryEntity> findAllByQueryAndApiName(String query, String apiName);
}
