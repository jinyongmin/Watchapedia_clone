package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.AdminUser;

import java.time.LocalDateTime;

public record AdminUserDto(
        Long adminIdx,
        String adminName,
        String adminId,
        String adminPw,
        String adminNumber,
        String adminType,
        LocalDateTime regDate
) {
    public static AdminUserDto of (
            Long adminIdx, String adminName,
            String adminId, String adminPw, String adminNumber,
            String adminType, LocalDateTime regDate

    ){
        return new AdminUserDto(
                adminIdx,adminName,adminId,adminPw,adminNumber,
                adminType, regDate);
    }


    public static AdminUserDto from(AdminUser entity){
        return new AdminUserDto(
                entity.getAdminIdx(),
                entity.getAdminName(),
                entity.getAdminId(),
                entity.getAdminPw(),
                entity.getAdminNumber(),
                entity.getAdminType(),
                entity.getRegDate()
        );
    }

    public AdminUser toEntity(){
        return AdminUser.of(
                adminName,
                adminId,
                adminPw,
                adminNumber,
                adminType
        );
    }
}