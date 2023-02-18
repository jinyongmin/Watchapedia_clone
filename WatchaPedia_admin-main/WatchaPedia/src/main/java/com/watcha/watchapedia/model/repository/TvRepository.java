package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.Tv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface TvRepository extends JpaRepository<Tv, Long> {
    Optional<Tv> findByTvIdx(Long TvIdx);
    Tv findTitleByTvIdx(Long TvIdx);
}
