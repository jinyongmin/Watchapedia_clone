package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByCommentUserName(String commentUserName);
////    List<Comment> findByQnaText(String qnaText);
List<Comment> findAll();
//    Optional<Comment> findByCommIdx(Long CommIdx);
}
