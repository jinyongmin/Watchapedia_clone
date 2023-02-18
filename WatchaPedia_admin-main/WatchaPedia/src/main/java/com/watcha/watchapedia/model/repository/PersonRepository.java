package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.AdminUser;
import com.watcha.watchapedia.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByPerNameContaining(String searchKey);
}
