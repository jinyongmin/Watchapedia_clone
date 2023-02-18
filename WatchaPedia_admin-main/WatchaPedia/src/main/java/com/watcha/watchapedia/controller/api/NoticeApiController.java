package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.Notice;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.NoticeApiRequest;
import com.watcha.watchapedia.model.network.request.NoticeStatusApiRequest;
import com.watcha.watchapedia.model.network.response.BookApiResponse;
import com.watcha.watchapedia.model.network.response.NoticeApiResponse;
import com.watcha.watchapedia.service.NoticeApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeApiController extends CrudController<NoticeApiRequest, NoticeApiResponse, Notice> {
    private final NoticeApiLogicService noticeApiLogicService;

    @PostMapping("")    // http://localhost:9090/api/notice
    public Header<NoticeApiResponse> cr(@RequestBody Header<NoticeApiRequest> request, HttpServletRequest httpServletRequest) {
        System.out.println("cr메소드로 잘 들어옴!");
        return noticeApiLogicService.cr(request,httpServletRequest);
    }

    @Override
    public Header<NoticeApiResponse> create(Header<NoticeApiRequest> request) {
        return super.create(request);
    }

    @Override
    @GetMapping("{ntcIdx}")    // http://localhost:9090/api/notice/{id} (get)
    public Header<NoticeApiResponse> read(@PathVariable(name="ntcIdx") Long ntcIdx) {
        return noticeApiLogicService.read(ntcIdx);
    }


    @Override
    @PutMapping("")    // http://localhost:9090/api/notice (put)
    public Header<NoticeApiResponse> update(@RequestBody Header<NoticeApiRequest> request) {
        return noticeApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{ntcIdx}")    // http://localhost:9090/api/notice/{id} (delete)
    public Header<NoticeApiResponse> delete(@PathVariable(name="ntcIdx") Long ntcIdx) {
        return noticeApiLogicService.delete(ntcIdx);
    }


    @PutMapping("/statusupdate")    // http://localhost:9090/api/notice/statusupdate/(put)
    public Header<NoticeApiResponse> statusupdate(@RequestBody Header<NoticeStatusApiRequest> request) {
        System.out.println("잘들어옴");
        System.out.println(request.getData());
        return noticeApiLogicService.statusupdate(request);
    }

}
