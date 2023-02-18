package com.watcha.watchapedia.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "tbQna")
@Builder
@Data
@ToString(callSuper = true)

public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaIdx; // 번호
    private String qnaText; // 문의내용
    private Long qnaUserIdx; // 사용자 번호
    @Column(length = 200)
    private String qnaUserid; // 사용자 이름
    private LocalDateTime qnaRegDate; // 문의 날짜
    @Column
    private String qnaAttach; // qna 등록
    @Column(length = 200)
    private String qnaStatus; // qna 상태
    private Long qnaAuserIdx; // 응답 번호
    @Column(length = 200)
    private String qnaAuserid; // 응답 아이디
    private String qnaAtext; // 설명
    private LocalDateTime qnaAregDate; // 응답 날짜
    @Column(length = 200)
    private String qnaAnswer; // 답변여부
    @Column(length = 100)
    private String qnaName; // qna 이메일
    @Column(length = 2000)
    private String qnaFile; // 첨부파일
    @Column(length = 2000)
    private String qnaDtext; // 답변 내용

    protected Qna() {
    }

    private Qna(Long qnaIdx, String qnaText, Long qnaUserIdx, String qnaUserid,
                LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
                Long qnaAuserIdx, String qnaAuserid, String qnaAtext, LocalDateTime qnaAregDate,
                String qnaAnswer, String qnaName, String qnaFile,
                String qnaDtext) {
        this.qnaIdx = qnaIdx;
        this.qnaText = qnaText;
        this.qnaUserIdx = qnaUserIdx;
        this.qnaUserid = qnaUserid;
        this.qnaRegDate = qnaRegDate;
        this.qnaAttach = qnaAttach;
        this.qnaStatus = qnaStatus;
        this.qnaAuserIdx = qnaAuserIdx;
        this.qnaAuserid = qnaAuserid;
        this.qnaAregDate = qnaAregDate;
        this.qnaAnswer = qnaAnswer;
        this.qnaAtext = qnaAtext;
        this.qnaName = qnaName;
        this.qnaFile = qnaFile;
        this.qnaDtext = qnaDtext;
    }

    public static Qna of(Long qnaIdx, String qnaText, Long qnaUserIdx, String qnaUserid,
                         LocalDateTime qnaRegDate, String qnaAttach, String qnaStatus,
                         Long qnaAuserIdx, String qnaAuserid, String qnaAtext, LocalDateTime qnaAregDate,
                         String qnaAnswer, String qnaName, String qnaFile,
                         String qnaDtext) {
        return new Qna(qnaIdx, qnaText, qnaUserIdx, qnaUserid,
                qnaRegDate, qnaAttach, qnaStatus, qnaAuserIdx, qnaAuserid, qnaAtext, qnaAregDate, qnaAnswer, qnaName, qnaFile, qnaDtext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qnaIdx);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Qna qna)) return false;
        return qnaIdx != null;
    }


//    public static Qna toUpdateEntity(QnaDto qnaDto) {
//        Qna qna = new Qna();
//        Qna.set(qnaDto.getId());
//        Qna.setBoardWriter(qnaDto.getBoardWriter());
//        Qna.setBoardPass(qnaDto.getBoardPass());
//        Qna.setBoardTitle(qnaDto.getBoardTitle());
//        Qna.setBoardContents(qnaDto.getBoardContents());
//        Qna.setBoardHits(qnaDto.getBoardHits());
//        return qna;
//    }
}