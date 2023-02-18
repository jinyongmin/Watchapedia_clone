package com.watcha.watchapedia.model.repository;
import com.watcha.watchapedia.model.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
    Optional<Webtoon> findByWebIdx(Long WebIdx);
    Webtoon findTitleByWebIdx(Long WebIdx);
}
