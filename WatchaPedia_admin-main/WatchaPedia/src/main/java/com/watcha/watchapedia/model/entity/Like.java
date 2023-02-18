package com.watcha.watchapedia.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "tbLike")
@Builder
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeIdx;
    @Setter @ManyToOne @JoinColumn(name = "like_user_idx")private User user;
    @Setter @ManyToOne @JoinColumn(name = "like_comment_idx")private Comment comment;
}
