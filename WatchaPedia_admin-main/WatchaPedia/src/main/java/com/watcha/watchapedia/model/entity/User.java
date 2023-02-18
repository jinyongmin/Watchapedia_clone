package com.watcha.watchapedia.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "tbUser")
@Builder
@AllArgsConstructor
@Data
@ToString(callSuper = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;
    @Column(length =100)
    private String userPw;
    private Long userSsn1;
    private Long userSsn2;
    @Column(length =100)
    private String userEmail;
    @Column(length =50)
    private String userStatus;
    private Long userCautionCnt;
    private Long userWarningCnt;
    private Long userSuspensionCnt;
    private LocalDateTime userLatelyStop;
    private LocalDateTime userReleaseDate;
    @Column(length =30)
    private String userType;
    @Column(length =50)
    private String userName;
    @Column(length =100)
    private String userLikeActor;
    @Column(length =100)
    private String userLikeDirector;
    @Column(length =100)
    private String userLikeGenre;

    @ToString.Exclude
    @OrderBy("likeIdx DESC")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();

    protected User() {}

    public User(String userPw, Long userSsn1, Long userSsn2, String userEmail, String userName) {
        this.userPw = userPw;
        this.userSsn1 = userSsn1;
        this.userSsn2 = userSsn2;
        this.userEmail = userEmail;
        this.userName = userName;
    }

//    public static User of(
//            String userId, String userPw, Long userSsn1, Long userSsn2, String userEmail,
//            String userStatus, Long userCautionCnt, Long userWarningCnt,
//            Long userSuspensionCnt, LocalDateTime userLatelyStop,
//            LocalDateTime userReleaseDate, String userType, String userName,
//            String userLikeActor, String userLikeDirector, String userLikeGenre
//    ) {
//        return new User(
//                userId,userPw,userSsn1,userSsn2,userEmail,userStatus,userCautionCnt,
//                userWarningCnt,userSuspensionCnt,userLatelyStop,userReleaseDate,userType,
//                userName,userLikeActor,userLikeDirector,userLikeGenre
//        );
//    }
    public static User of(
            String userPw, Long userSsn1, Long userSsn2, String userEmail,
            String userName
    ) {
        return new User(
                userPw,userSsn1,userSsn2,userEmail, userName
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdx);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof User user)) return false;
        return userIdx != null;
    }
}