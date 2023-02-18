package com.watcha.watchapedia.model.network.response;

import com.watcha.watchapedia.model.dto.LikeDto;
import com.watcha.watchapedia.model.entity.Comment;
import com.watcha.watchapedia.model.entity.Like;
import com.watcha.watchapedia.model.entity.User;

public record LikeResponseDto(
        Long likeIdx,
        User user,
        Comment comment
) {
    public static LikeResponseDto of(Long likeIdx, User user, Comment comment){
        return new LikeResponseDto(likeIdx, user, comment);
    }

    public static LikeResponseDto from(LikeDto like){
        return new LikeResponseDto(
                like.likeIdx(),
                like.user(),
                like.comment()
        );
    }

}
