package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.Comment;
import com.watcha.watchapedia.model.entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    List<Recomment> findByComment_CommIdx(Long id);
}
