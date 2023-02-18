package com.watcha.watchapedia.model.network.response;



import com.watcha.watchapedia.model.dto.CommentDto;
import com.watcha.watchapedia.model.entity.Like;
import com.watcha.watchapedia.model.entity.Recomment;
import com.watcha.watchapedia.model.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        Long commIdx,
        Long commUserIdx,
        String commName,
        String commText,
        String commContentType,
        Long commContentIdx,
        LocalDateTime commRegDate,
        String commTitle,
        String commPosterUrl,
        List<Recomment> recommentList,
        List<Like> likeList

)implements Serializable {
    //of : 값들을 매개변수로 받아서 => Record형으로 만듬
    public static CommentResponse of(
            Long commIdx, Long commUserIdx, String commName, String commText,
            String commContentType, Long commContentIdx, LocalDateTime commRegDate,String commTitle,String commPosterUrl,List<Recomment> recommentList,List<Like> likeList
            ){
        return new CommentResponse(commIdx, commUserIdx, commName, commText,
                commContentType,commContentIdx,commRegDate,commTitle,commPosterUrl,recommentList,likeList);
    }

    //from : dto를 매개변수로 받아서 => Record형으로 만듬
    public static CommentResponse from (CommentDto dto){

        return new CommentResponse(
                dto.commIdx(),
                dto.commUserIdx(),
                dto.commName(),
                dto.commText(),
                dto.commContentType(),
                dto.commContentIdx(),
                dto.commRegDate(),
                null,
                null,
                dto.recommentList(),
                dto.likeList()
        );
    }

    //commTitle 집어넣기
    public static CommentResponse insertTitle (CommentResponse dto, String str){

        return new CommentResponse(
                dto.commIdx(),
                dto.commUserIdx(),
                dto.commName(),
                dto.commText(),
                dto.commContentType(),
                dto.commContentIdx(),
                dto.commRegDate(),
                str,
                dto.commPosterUrl(),
                dto.recommentList(),
                dto.likeList
        );
    }

    public static CommentResponse inserPosterUrl(CommentResponse dto, String str){
        return new CommentResponse(
                dto.commIdx(),
                dto.commUserIdx(),
                dto.commName(),
                dto.commText(),
                dto.commContentType(),
                dto.commContentIdx(),
                dto.commRegDate(),
                dto.commTitle(),
                str,
                dto.recommentList(),
                dto.likeList()
        );
    }


}
