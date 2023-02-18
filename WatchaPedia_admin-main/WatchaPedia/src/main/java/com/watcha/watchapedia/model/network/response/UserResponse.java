package com.watcha.watchapedia.dto.response;


import com.watcha.watchapedia.model.dto.UserDto;
import com.watcha.watchapedia.model.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;


public record UserResponse(
    // 고유 번호
    Long userIdx,
    // 이름
     String userName,
    // 회원 유형(인플루언서)
     String userType,
    // 계정 상태
     String userStatus,

    // 주민등록번호1
     Long userSsn1,
    // 주민등록번호2
     Long userSsn2,

    // 주의
     Long userCautionCnt,
    // 경고
    Long userWarningCnt,
    // 정지
    Long userSuspensionCnt,
    // 최근 정지 날짜
     LocalDateTime userLatelyStop,
    // 정지 해제 날짜
     LocalDateTime userReleaseDate
) implements Serializable {
    public static UserResponse of(
            Long userIdx,
            String userName, String userType, String userId, String userStatus, Long userSsn1,
            Long userSsn2, Long userCautionCnt, Long userWarningCnt, Long userSuspensionCnt,
            LocalDateTime userLatelyStop, LocalDateTime userReleaseDate){
        return new UserResponse(userIdx,userName, userType, userStatus, userSsn1, userSsn2,
                userCautionCnt, userWarningCnt, userSuspensionCnt, userLatelyStop, userReleaseDate);
    }

    public static UserResponse from(UserDto dto){
        return new UserResponse(
                dto.userIdx(),
                dto.userName(),
                dto.userType(),
                dto.userStatus(),
                dto.userSsn1(),
                dto.userSsn2(),
                dto.userCautionCnt(),
                dto.userWarningCnt(),
                dto.userSuspensionCnt(),
                dto.userLatelyStop(),
                dto.userReleaseDate()
        );
    }
}
