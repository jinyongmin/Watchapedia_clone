package com.watcha.watchapedia.model.repository;

import com.watcha.watchapedia.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReportRepository extends JpaRepository<Report, Long> {

}
