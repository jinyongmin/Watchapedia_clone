package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(
        name = "tbWebtoon"
)
public class Webtoon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
