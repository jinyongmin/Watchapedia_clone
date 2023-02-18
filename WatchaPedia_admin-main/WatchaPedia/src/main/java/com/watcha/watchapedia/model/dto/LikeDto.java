package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.Comment;
import com.watcha.watchapedia.model.entity.Like;
import com.watcha.watchapedia.model.entity.Recomment;
import com.watcha.watchapedia.model.entity.User;

import java.time.LocalDateTime;

public record LikeDto(
        Long likeIdx,
        User user,
        Comment comment
) {
    public static LikeDto of(Long likeIdx, User user, Comment comment){
        return new LikeDto(likeIdx, user, comment);
    }

    public static LikeDto from(Like like){
        return new LikeDto(
                like.getLikeIdx(),
                like.getUser(),
                like.getComment()
        );
    }

}
