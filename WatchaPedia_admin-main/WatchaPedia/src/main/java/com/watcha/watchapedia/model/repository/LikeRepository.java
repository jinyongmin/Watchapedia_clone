package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.Like;
import com.watcha.watchapedia.model.entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByComment_CommIdx(Long id);
}
