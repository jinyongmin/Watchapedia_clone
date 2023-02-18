package com.watcha.watchapedia.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdvertiseApiRequest {
    private Long adIdx;
    private String adTitle;
    private String adDescription;
    private String adStatus;
    private String adVideosource;
    private String adImagesource;
    private String adBtnLink;
    private String adBtnColor;
    private String adBtnText;
    private String adClient;
    private String adClientLogoimage;
    private String endDate;
}
