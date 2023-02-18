package com.watcha.watchapedia.model.repository;


import com.watcha.watchapedia.model.entity.AdminUser;
import com.watcha.watchapedia.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByNtcIdx(Long ntcIdx);
    List<Notice> findByNtcStatusOrderByNtcIdxDesc(String status);

}
