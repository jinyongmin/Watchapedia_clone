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
        name = "tbMovie"
)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
