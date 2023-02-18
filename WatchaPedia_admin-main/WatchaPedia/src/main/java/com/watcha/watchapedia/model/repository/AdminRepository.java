package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AdminRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByAdminId(String adminId);
    Optional<AdminUser> findByAdminNumber(String adminNumber);
    Optional<AdminUser> findByAdminIdAndAdminPw(String userid, String userpw);

    AdminUser findAppleByAdminIdx(Long id);

    List<AdminUser> findAllByOrderByAdminIdxDesc();
}
