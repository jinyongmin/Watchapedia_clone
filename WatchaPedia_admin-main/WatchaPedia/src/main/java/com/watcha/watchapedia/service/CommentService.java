package com.watcha.watchapedia.service;


import com.watcha.watchapedia.model.dto.CommentDto;
import com.watcha.watchapedia.model.entity.Comment;
import com.watcha.watchapedia.model.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    final CommentRepository commentRepository;
    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<CommentDto> searchComments(){
        //return commentRepository.findAll();
        return commentRepository.findAll().stream().map(CommentDto::from).toList();
    }

//    @Transactional(readOnly = true)
//    public CommentDto getComment(Long commentIdx){
//        return commentRepository.findById(commentIdx)
//                .map(CommentDto::from)
//                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - commentIdx: " + commentIdx));
//    }
    //    public void saveQna(QnaDto dto){
//        Qna qna = qnaRepository.getReferenceById(dto.qnaIdx());
//        qnaRepository.save(dto.toEntity(getQna()));
//    }
//    public void updateComment(Long commentIdx, CommentDto dto){
//        try{
//            Comment comment = commentRepository.getReferenceById(commentIdx);
//            if(dto.commText() != null) { comment.setCommText(dto.commText());}
//            if(dto.commIdx() != null) { comment.setCommIdx(dto.commIdx());}
//        }catch(EntityNotFoundException e){
//            e.printStackTrace();
//        }
//    }
}
