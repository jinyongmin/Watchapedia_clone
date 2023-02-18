package com.watcha.watchapedia.dto.request;

import com.watcha.watchapedia.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UserRequest(
        String userPw,
        Long userSsn1,
        Long userSsn2,
        String userEmail,
        String userName
){
  public static UserRequest of(String userPw, Long userSsn1, Long userSsn2,
                               String userEmail, String userName) {
    return new UserRequest(userPw, userSsn1, userSsn2, userEmail, userName);
  }

  public UserDto toDto() {
    return UserDto.of(
            userPw, userSsn1, userSsn2,
            userEmail, userName
    );
  }
}
