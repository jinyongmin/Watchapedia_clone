package com.watcha.watchapedia.model.network.response;



import com.watcha.watchapedia.model.dto.AdminUserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AdminUserResponse(
        Long adminIdx,
        String adminName,
        String adminId,
        String adminPw,
        String adminNumber,
        String adminType,
        LocalDateTime regDate

) implements Serializable {
    public static AdminUserResponse of(
            Long adminIdx, String adminName,
            String adminId, String adminPw,
            String adminNumber, String adminType,
            LocalDateTime regDate
    ){
        return new AdminUserResponse(adminIdx,adminName,adminId,adminPw,adminNumber,adminType,regDate);
    }

    public static AdminUserResponse from(AdminUserDto dto){
        return new AdminUserResponse(
                dto.adminIdx(),
                dto.adminName(),
                dto.adminId(),
                dto.adminPw(),
                dto.adminNumber(),
                dto.adminType(),
                dto.regDate()
        );
    }

}
