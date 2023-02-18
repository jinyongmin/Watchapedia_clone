package com.watcha.watchapedia.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebtoonApiResponse {
    private Long webIdx;
    private String webThumbnail;
    private String webTitle;
    private String webTitleOrg;
    private String webWriter;
    private String webGenre;
    private String webSerDetail;
    private String webSerDay;
    private String webSerPeriod;
    private String webAge;
    private String webSummary;
    private String webPeople;
    private String webWatch;
    private String webBackImg;
}
