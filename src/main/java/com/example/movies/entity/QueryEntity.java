package com.example.movies.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "query", uniqueConstraints = {@UniqueConstraint(columnNames = {"query_", "api_name"})})
public class QueryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "query_")
    private String query;

    @Column(nullable = false, name = "api_name")
    private String apiName;
}
