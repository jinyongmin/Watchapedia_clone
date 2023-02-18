package com.watcha.watchapedia.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminApiResponse {
     private Long adminIdx;
     private String adminName;
     private String adminId;
     private String adminPw;
     private String adminNumber;
     private String adminType;

}
