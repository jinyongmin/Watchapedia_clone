package com.watcha.watchapedia.service;



import com.watcha.watchapedia.model.dto.QnaDto;
import com.watcha.watchapedia.model.entity.Movie;
import com.watcha.watchapedia.model.entity.Notice;
import com.watcha.watchapedia.model.entity.Qna;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.MovieApiRequest;
import com.watcha.watchapedia.model.network.request.QnaApiRequest;
import com.watcha.watchapedia.model.network.request.QnaRequest;
import com.watcha.watchapedia.model.network.response.NoticeApiResponse;
import com.watcha.watchapedia.model.network.response.QnaApiResponse;
import com.watcha.watchapedia.model.network.response.QnaResponse;
import com.watcha.watchapedia.model.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    final QnaRepository qnaRepository;

    private QnaApiResponse response(Qna qna){
        QnaApiResponse qnaApiResponse = QnaApiResponse.builder()
                .qnaIdx(qna.getQnaIdx())
                .qnaText(qna.getQnaText())
                .qnaStatus(qna.getQnaStatus())
                .build();
        return qnaApiResponse;
    }

    @Transactional(readOnly = true) //데이터를 불러오기만 할 때(수정X)
    public List<Qna> searchQnas(){
        return qnaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public QnaDto getQna(Long qnaIdx){
        return qnaRepository.findById(qnaIdx)
                .map(QnaDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - qnaidx: " + qnaIdx));
    }

    public void saveQna(QnaDto dto){
        qnaRepository.save(dto.toEntity());

    }


    public Header<QnaApiResponse> updateQna(Header<QnaApiRequest> request){
        QnaApiRequest qnaApiRequest = request.getData();
        Optional<Qna> qnas = qnaRepository.findByQnaIdx(qnaApiRequest.getQnaIdx());

        return qnas.map(
                        qna -> {
                            qna.setQnaDtext(qnaApiRequest.getQnaDtext());
                            qna.setQnaStatus("답변완료");
                            return qna;
                        }).map(qna -> qnaRepository.save(qna))
                .map(qna -> response(qna))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );
    }
}


