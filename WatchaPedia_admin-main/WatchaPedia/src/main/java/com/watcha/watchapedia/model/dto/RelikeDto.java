package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.*;

public record RelikeDto(
        Long relikeIdx,
        Recomment recomment,
        User user
) {
    public static RelikeDto of(Long relikeIdx, Recomment recomment, User user){
        return new RelikeDto(relikeIdx, recomment, user);
    }

    public static RelikeDto from(Relike relike){
        return new RelikeDto(
                relike.getRelikeIdx(),
                relike.getRecomment(),
                relike.getUser()
        );
    }

}
