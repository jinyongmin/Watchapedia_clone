package com.watcha.watchapedia.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieApiResponse {
    private Long movIdx;
    private String movThumbnail;
    private String movTitle;
    private String movTitleOrg;
    private String movMakingDate;
    private String movCountry;
    private String movGenre;
    private String movTime;
    private String movAge;
    private String movPeople;
    private String movSummary;
    private String movGallery;
    private String movVideo;
    private String movWatch;
    private String movBackImg;
}
