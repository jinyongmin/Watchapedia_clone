package com.watcha.watchapedia.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WebtoonApiRequest {
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
