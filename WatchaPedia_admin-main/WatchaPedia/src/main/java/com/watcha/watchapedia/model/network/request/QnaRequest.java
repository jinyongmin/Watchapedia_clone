package com.watcha.watchapedia.model.network.request;


import com.watcha.watchapedia.model.dto.QnaDto;

import java.time.LocalDateTime;

public record QnaRequest(
        Long qnaIdx, String qnaText, Long qnaUserIdx, String qnaUserid,
        LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
        Long qnaAuserIdx, String qnaAuserid, String qnaAtext,   LocalDateTime qnaAregDate,
        String qnaAnswer, String qnaName, String qnaFile,
        String qnaDtext
) {

    public static QnaRequest of(Long qnaIdx, String qnaText, Long qnaUserIdx, String qnaUserid,
                                         LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
                                         Long qnaAuserIdx, String qnaAuserid, String qnaAtext,   LocalDateTime qnaAregDate,
                                         String qnaAnswer, String qnaName, String qnaFile,
                                         String qnaDtext) {
        return new QnaRequest(qnaIdx, qnaText, qnaUserIdx, qnaUserid,
                qnaRegDate,qnaAttach,qnaStatus,qnaAuserIdx,qnaAuserid,qnaAtext, qnaAregDate, qnaAnswer,qnaName,qnaFile,qnaDtext);
    }

    public QnaDto toDto() {
        System.out.println();
        return QnaDto.of(
                qnaIdx, qnaText, qnaUserIdx, qnaUserid,
                qnaRegDate,qnaAttach,qnaStatus,qnaAuserIdx,qnaAuserid,qnaAtext, qnaAregDate, qnaAnswer,qnaName,qnaFile,qnaDtext
        );
    }
}