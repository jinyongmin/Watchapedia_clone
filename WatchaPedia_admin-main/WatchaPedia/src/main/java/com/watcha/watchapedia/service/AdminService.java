package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.dto.AdminUserDto;
import com.watcha.watchapedia.model.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    final AdminRepository adminRepository;
    public List<AdminUserDto> findAllReport(){
        return adminRepository.findAllByOrderByAdminIdxDesc().stream().map(AdminUserDto::from).toList();
    }
}
