package com.watcha.watchapedia.model.network.request;


import com.watcha.watchapedia.model.dto.CommentDto;

import java.time.LocalDateTime;

public record CommentRequest(
        Long commIdx, Long commUserIdx, String commName, String commText,
        String commContentType, Long commContentIdx, LocalDateTime commRegDate

) {

    public static CommentRequest of(  Long commIdx, Long commUserIdx, String commName, String commText,
                                      String commContentType, Long commContentIdx, LocalDateTime commRegDate
            ) {
        return new CommentRequest(commIdx, commUserIdx, commName, commText,
                commContentType,commContentIdx,commRegDate);
    }

    public CommentDto toDto() {
        return CommentDto.of(
                commIdx, commUserIdx, commName, commText,
                commContentType,commContentIdx,commRegDate,null,null
        );
    }
}