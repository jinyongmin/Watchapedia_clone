package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.QnaApiRequest;
import com.watcha.watchapedia.model.network.response.QnaApiResponse;
import com.watcha.watchapedia.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaApiController {
    private final QnaService qnaService;

    @PostMapping("/qnaupdate")
    public Header<QnaApiResponse> updateQna(@RequestBody Header<QnaApiRequest> request) {
        return qnaService.updateQna(request);
    }

}
