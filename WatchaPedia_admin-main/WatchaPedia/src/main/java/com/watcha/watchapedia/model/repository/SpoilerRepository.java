package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.Spoiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpoilerRepository extends JpaRepository<Spoiler, Long> {
    Optional<Spoiler> findBySpoCommentIdx(Long comment);
}
