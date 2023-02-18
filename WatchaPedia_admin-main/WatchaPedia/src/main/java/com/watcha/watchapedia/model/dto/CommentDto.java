package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.Comment;
import com.watcha.watchapedia.model.entity.Like;
import com.watcha.watchapedia.model.entity.Recomment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDto(
        Long commIdx,
        Long commUserIdx,
        String commName,
        String commText,
        String commContentType,
        Long commContentIdx,
        LocalDateTime commRegDate,
        List<Recomment> recommentList,
        List<Like> likeList

) {
    public static CommentDto of(
            Long commIdx, Long commUserIdx, String commName, String commText,
            String commContentType, Long commContentIdx, LocalDateTime commRegDate, List<Recomment> recommentList, List<Like> likeList
    ){

        return new CommentDto(commIdx, commUserIdx, commName, commText,
                commContentType,commContentIdx,commRegDate,recommentList,likeList
        );
    }
    public static CommentDto of(
            Long commIdx, Long commUserIdx, String commName, String commText,
            String commContentType, LocalDateTime commRegDate

    ){

        return new CommentDto(commIdx, commUserIdx, commName, commText,
                commContentType,null,commRegDate,null,null
        );
    }

    public static CommentDto from(Comment entity){
        return new CommentDto(
                entity.getCommIdx(),
                entity.getCommUserIdx(),
                entity.getCommName(),
                entity.getCommText(),
                entity.getCommContentType(),
                entity.getCommContentIdx(),
                entity.getCommRegDate(),
                entity.getRecommentList(),
                entity.getLikeList()
        );
    }
    public Comment toEntity(){
        return  Comment.of(
                commIdx, commUserIdx, commName, commText,
                commContentType,commContentIdx,commRegDate
        );
    }
}
