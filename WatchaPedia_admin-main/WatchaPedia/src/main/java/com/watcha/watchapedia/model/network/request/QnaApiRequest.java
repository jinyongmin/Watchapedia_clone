package com.watcha.watchapedia.model.network.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QnaApiRequest {
    private Long qnaIdx;
    private String qnaDtext;
    private String qnaStatus;
}