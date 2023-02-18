package com.watcha.watchapedia.model.dto;

import com.watcha.watchapedia.model.entity.Comment;
import com.watcha.watchapedia.model.entity.Recomment;
import com.watcha.watchapedia.model.entity.Relike;
import com.watcha.watchapedia.model.entity.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public record RecommentDto(
        Long id,
        Comment comment,
        User user,
        String recommName,
        String recommText,
        LocalDateTime recommRegDate,
        List<Relike> relikeList

) {
    public static RecommentDto of(Long id, Comment comment, User user, String recommName, String recommText, LocalDateTime recommRegDate, List<Relike> relikeList){
        return new RecommentDto(id, comment, user,recommName, recommText, recommRegDate,relikeList);
    }

    public static RecommentDto from(Recomment recomment){
        return new RecommentDto(
          recomment.getRecommIdx(),
          recomment.getComment(),
          recomment.getUser(),
          recomment.getRecommName(), recomment.getRecommText(), recomment.getRecommRegDate(),
                recomment.getRelikeList()
        );
    }




}
