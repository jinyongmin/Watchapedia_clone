package com.watcha.watchapedia.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterApiResponse {
    private Long perIdx;
    private String perName;
    private String perPhoto;
    private String perRole;
    private String perMov ;
    private String perBook ;
    private String perWebtoon;
    private String perTv;
    private String perBiography;
}
