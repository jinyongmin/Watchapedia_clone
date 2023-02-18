package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.dto.UserDto;
import com.watcha.watchapedia.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByUserIdxDesc();
}
