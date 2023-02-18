package com.watcha.watchapedia.model.repository;


import com.watcha.watchapedia.model.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByMovIdx(Long movIdx);
    Movie findTitleByMovIdx(Long movIdx);
}
