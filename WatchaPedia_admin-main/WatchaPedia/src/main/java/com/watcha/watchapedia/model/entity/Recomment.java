package com.watcha.watchapedia.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper=true)
@EntityListeners(AuditingEntityListener.class)
@Entity(name="tbRecomment")
public class Recomment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommIdx;
    @Setter @ManyToOne @JoinColumn(name = "recomm_comm_idx")private Comment comment;
    @Setter @ManyToOne @JoinColumn(name = "recomm_user_idx")private User user;
    @Setter @Column(length = 20) private String recommName;
    @Setter private String recommText;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @CreatedDate @Column(nullable = false) private LocalDateTime recommRegDate;

    @ToString.Exclude
    @OrderBy("relikeIdx DESC")
    @OneToMany(mappedBy = "recomment", cascade = CascadeType.ALL)
    private final List<Relike> relikeList = new ArrayList<>();

}
