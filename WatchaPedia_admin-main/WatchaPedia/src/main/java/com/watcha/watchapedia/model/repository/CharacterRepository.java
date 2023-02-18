package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByPerIdx(Long perIdx);
}
