package com.watcha.watchapedia.model.network.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QnaApiResponse {
    private Long qnaIdx;
    private String qnaText;
    private String qnaStatus;
}