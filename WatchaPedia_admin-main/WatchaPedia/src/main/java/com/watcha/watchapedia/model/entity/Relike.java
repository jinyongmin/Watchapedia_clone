package com.watcha.watchapedia.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "tbRecommentLike")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Relike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relikeIdx;
    @Setter @ManyToOne @JoinColumn(name = "relike_recomm_idx")private Recomment recomment;
    @Setter @ManyToOne @JoinColumn(name = "relike_user_idx")private User user;
}
