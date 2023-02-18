package com.watcha.watchapedia.model.repository;


import com.watcha.watchapedia.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookIdx(Long bookIdx);
    Book findTitleByBookIdx(Long bookIdx);
}
