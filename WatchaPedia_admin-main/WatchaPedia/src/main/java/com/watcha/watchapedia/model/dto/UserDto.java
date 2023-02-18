package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.Like;
import com.watcha.watchapedia.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserDto (
    Long userIdx,
    String userPw,
    Long userSsn1,
    Long userSsn2,
    String userEmail,
    String userStatus,
    Long userCautionCnt,
    Long userWarningCnt,
    Long userSuspensionCnt,
    LocalDateTime userLatelyStop,
    LocalDateTime userReleaseDate,
    String userType,
    String userName,
    String userLikeActor,
    String userLikeDirector,
    String userLikeGenre,
    List<Like> likeList
){
    public static UserDto of(
            Long userIdx, String userPw, Long userSsn1, Long userSsn2,
            String userEmail, String userStatus, Long userCautionCnt, Long userWarningCnt,
            Long userSuspensionCnt, LocalDateTime userLatelyStop, LocalDateTime userReleaseDate,
            String userType, String userName, String userLikeActor, String userLikeDirector,
            String userLikeGenre,List<Like> likeList
    ){
        return new UserDto(
                userIdx, userPw, userSsn1, userSsn2, userEmail, userStatus,
                userCautionCnt, userWarningCnt, userSuspensionCnt, userLatelyStop,
                userReleaseDate, userType, userName, userLikeActor, userLikeDirector,
                userLikeGenre,likeList
        );
    }

    public static UserDto of(
            String userPw, Long userSsn1, Long userSsn2,
            String userEmail, String userName
    ){
        return new UserDto(
                null, userPw, userSsn1, userSsn2, userEmail, null,
                null, null, null, null, null, null, userName, null, null,
                null,null
        );
    }

    public static UserDto from(User entity){
        return new UserDto(
                entity.getUserIdx(),
                entity.getUserPw(),
                entity.getUserSsn1(),
                entity.getUserSsn2(),
                entity.getUserEmail(),
                entity.getUserStatus(),
                entity.getUserCautionCnt(),
                entity.getUserWarningCnt(),
                entity.getUserSuspensionCnt(),
                entity.getUserLatelyStop(),
                entity.getUserReleaseDate(),
                entity.getUserType(),
                entity.getUserName(),
                entity.getUserLikeActor(),
                entity.getUserLikeDirector(),
                entity.getUserLikeGenre(),
                entity.getLikeList()
        );
    }

    public User toEntity(){
        return User.of(
                userPw, userSsn1, userSsn2,
                userEmail, userName
        );
    }
}
