package com.watcha.watchapedia.model.network.response;



import com.watcha.watchapedia.model.dto.QnaDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record QnaResponse(
        Long qnaIdx,
        String qnaText,
        Long qnaUserIdx,
        String qnaUserid,
        LocalDateTime qnaRegDate,
        String qnaAttach,
        String qnaStatus,
        Long qnaAuserIdx,
        String qnaAuserid,
        String qnaAtext,
        LocalDateTime qnaAregDate,
        String qnaAnswer,
        String qnaName,
        String qnaFile,
        String qnaDtext
)implements Serializable {

    public static QnaResponse of(
            Long qnaIdx, String qnaText, Long qnaUserIdx, String qnaUserid,
            LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
            Long qnaAuserIdx, String qnaAuserid, String qnaAtext,   LocalDateTime qnaAregDate,
            String qnaAnswer, String qnaName, String qnaFile,
            String qnaDtext){
        return new QnaResponse(qnaIdx, qnaText, qnaUserIdx, qnaUserid,
                qnaRegDate,qnaAttach,qnaStatus,qnaAuserIdx,qnaAuserid,qnaAtext, qnaAregDate, qnaAnswer,qnaName,qnaFile,qnaDtext);
    }

    public static QnaResponse from (QnaDto dto){





        String atext = dto.qnaAtext();
        if(dto.qnaAtext() == null){
            atext = "설명없음";
        }
        

        String fileData = dto.qnaFile();
        if(dto.qnaFile() == null){
            fileData = "파일없음";
        }

        return new QnaResponse(
                dto.qnaIdx(),
                dto.qnaText(),
                dto.qnaUserIdx(),
                dto.qnaUserid(),
                dto.qnaRegDate(),
                dto.qnaAttach(),
                dto.qnaStatus(),
                dto.qnaAuserIdx(),
                dto.qnaAuserid(),
                atext,
                dto.qnaAregDate(),
                dto.qnaAnswer(),
                dto.qnaName(),
                fileData,
                dto.qnaDtext()
        );
    }
}