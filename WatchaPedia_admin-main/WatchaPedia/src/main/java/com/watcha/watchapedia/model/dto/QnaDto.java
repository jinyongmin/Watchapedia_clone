package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.Qna;

import java.time.LocalDateTime;

public record QnaDto(
        Long qnaIdx, String qnaText, Long qnaUserIdx, String qnaUserid,
        LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
        Long qnaAuserIdx, String qnaAuserid, String qnaAtext,   LocalDateTime qnaAregDate,
        String qnaAnswer, String qnaName, String qnaFile,
        String qnaDtext
) {
    public static QnaDto of(

            String qnaText, Long qnaUserIdx, String qnaUserid,
            LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
            Long qnaAuserIdx,
            String qnaAuserid, String qnaAtext,   LocalDateTime qnaAregDate,
            String qnaAnswer, String qnaName, String qnaFile,
            String qnaDtext
    ){

        return new QnaDto(null, qnaText, qnaUserIdx, qnaUserid,
                qnaRegDate,qnaAttach,qnaStatus,qnaAuserIdx,qnaAuserid,qnaAtext, qnaAregDate, qnaAnswer,qnaName,qnaFile,qnaDtext
        );
    }

    public static QnaDto of(
            Long qnaIdx, String qnaText, Long qnaUserIdx, String qnaUserid,
            LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
            Long qnaAuserIdx, String qnaAuserid, String qnaAtext, LocalDateTime qnaAregDate,
            String qnaAnswer, String qnaName, String qnaFile,
            String qnaDtext
    ){

        return new QnaDto(qnaIdx, qnaText, qnaUserIdx, qnaUserid,
                qnaRegDate,qnaAttach,qnaStatus,qnaAuserIdx,qnaAuserid,qnaAtext, qnaAregDate, qnaAnswer,qnaName,qnaFile,qnaDtext
        );
    }

    public static QnaDto from(Qna entity){
        return new QnaDto(
                entity.getQnaIdx(),
                entity.getQnaText(),
                entity.getQnaUserIdx(),
                entity.getQnaUserid(),
                entity.getQnaRegDate(),
                entity.getQnaAttach(),
                entity.getQnaStatus(),
                entity.getQnaAuserIdx(),
                entity.getQnaAuserid(),
                entity.getQnaAtext(),
                entity.getQnaAregDate(),
                entity.getQnaAnswer(),
                entity.getQnaName(),
                entity.getQnaFile(),
                entity.getQnaDtext()
        );
    }
    public Qna toEntity(){
        return  Qna.of(
                qnaIdx, qnaText, qnaUserIdx, qnaUserid,
                qnaRegDate,qnaAttach,qnaStatus,qnaAuserIdx,qnaAuserid,qnaAtext, qnaAregDate, qnaAnswer,qnaName,qnaFile,qnaDtext


        );
    }
}
