package com.watcha.watchapedia.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminApiRequest {
    private Long adminIdx;
    private String adminName;
    private String adminId;
    private String adminPw;
    private String adminNumber;
    private String adminType;
    private String oldPw;
}
