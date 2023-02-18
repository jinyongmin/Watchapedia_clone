package com.watcha.watchapedia.model.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "tbComment")
@Builder
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commIdx; // 번호
    private Long commUserIdx; // 사용자 고유번호
    private String commName; // 사용자 이름
    @Column(length = 200)
    private String commText; // 코멘트내용
    private String commContentType; // 콘텐츠유형
    @Column
    private Long commContentIdx; // 콘텐츠고유번호
    @Column(length = 200)
    private LocalDateTime commRegDate; // 등록날짜

    //Recomment Entity의 comment컬럼("recomm_comm_idx")가 우리 Long id와 같은 걸 담은 List
    @ToString.Exclude
    @OrderBy("recommIdx DESC")
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<Recomment> recommentList = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("likeIdx DESC")
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<Like> likeList = new ArrayList<>();



//    protected Comment() {}
//    private Comment(Long commIdx, Long commUserIdx, String commName, String commText,
//                    String commContentType, Long commContentIdx, LocalDateTime commRegDate
//                        ) {
//        this.commIdx = commIdx;
//        this.commUserIdx = commUserIdx;
//        this.commName = commName;
//        this.commText = commText;
//        this.commContentType = commContentType;
//        this.commContentIdx = commContentIdx;
//        this.commRegDate = commRegDate;
//    }

    public static Comment of(Long commIdx, Long commUserIdx, String commName, String commText,
                             String commContentType, Long commContentIdx, LocalDateTime commRegDate
            ) {
        return new Comment(commIdx, commUserIdx, commName, commText,
                commContentType,commContentIdx,commRegDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commIdx);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Comment comment)) return false;
        return commIdx != null;
    }
}
