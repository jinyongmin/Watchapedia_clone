package com.watcha.watchapedia.service;


import com.watcha.watchapedia.model.entity.Movie;
import com.watcha.watchapedia.model.entity.Notice;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.NoticeApiRequest;
import com.watcha.watchapedia.model.network.request.NoticeStatusApiRequest;
import com.watcha.watchapedia.model.network.response.BookApiResponse;
import com.watcha.watchapedia.model.network.response.MovieApiResponse;
import com.watcha.watchapedia.model.network.response.NoticeApiResponse;
import com.watcha.watchapedia.model.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeApiLogicService extends BaseService<NoticeApiRequest, NoticeApiResponse, Notice> {

    private final NoticeRepository tbNoticeRepository;

    private NoticeApiResponse response(Notice tbNotice){
        NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                .ntcIdx(tbNotice.getNtcIdx())
                .ntcTitle(tbNotice.getNtcTitle())
                .ntcText(tbNotice.getNtcText())
                .ntcBtnText(tbNotice.getNtcBtnText())
                .ntcBtnColor(tbNotice.getNtcBtnColor())
                .ntcImagepath(tbNotice.getNtcImagepath())
                .ntcBtnLink(tbNotice.getNtcBtnLink())
                .build();
        return noticeApiResponse;
    }

    @Override
    public Header<NoticeApiResponse> create(Header<NoticeApiRequest> request) {
        return null;
    }

    public Header<NoticeApiResponse> cr(Header<NoticeApiRequest> request, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        NoticeApiRequest noticeApiRequest = request.getData();
        Notice newNotice = Notice.builder()
                .ntcTitle(noticeApiRequest.getNtcTitle())
                .ntcText(noticeApiRequest.getNtcText())
                .ntcImagepath(noticeApiRequest.getNtcImagepath())
                .ntcBtnColor(noticeApiRequest.getNtcBtnColor())
                .ntcBtnText(noticeApiRequest.getNtcBtnText())
                .ntcBtnLink(noticeApiRequest.getNtcBtnLink())
                .ntcRegBy((String)session.getAttribute("adminId"))
                .ntcStatus("미등록")
                .build();
        Notice newTbNotice = tbNoticeRepository.save(newNotice);
        return Header.OK(response(newTbNotice));
    }

    @Override
    public Header<NoticeApiResponse> read(Long ntcIdx) {
        return baseRepository.findById(ntcIdx).map(notice -> response(notice))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<NoticeApiResponse> update(Header<NoticeApiRequest> request) {
        System.out.println("update servie 들어왓어용~");
        NoticeApiRequest noticeApiRequest = request.getData();
        Optional<Notice> notices = tbNoticeRepository.findByNtcIdx(noticeApiRequest.getNtcIdx());
        return notices.map(
                        notice -> {
                            notice.setNtcTitle(noticeApiRequest.getNtcTitle());
                            notice.setNtcText(noticeApiRequest.getNtcText());
                            if(noticeApiRequest.getNtcImagepath()!=null){notice.setNtcImagepath(noticeApiRequest.getNtcImagepath());}
                            notice.setNtcBtnText(noticeApiRequest.getNtcBtnText());
                            notice.setNtcBtnLink(noticeApiRequest.getNtcBtnLink());
                            if(noticeApiRequest.getNtcBtnColor()!=null){notice.setNtcBtnColor(noticeApiRequest.getNtcBtnColor());}
                            return notice;
                        }).map(notice -> baseRepository.save(notice))
                .map(notice -> response(notice))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );

    }

    @Override
    public Header delete(Long ntcIdx) {
        Optional<Notice> notice = baseRepository.findById(ntcIdx);
        return notice.map(notice1 -> {
            baseRepository.delete(notice1);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }


    public List<Notice> noticeList(){
        return tbNoticeRepository.findAll();
    }

    public Header<NoticeApiResponse> statusupdate(Header<NoticeStatusApiRequest> request) {
        NoticeStatusApiRequest noticeStatusApiRequest = request.getData();
        Optional<Notice> notices = tbNoticeRepository.findByNtcIdx(noticeStatusApiRequest.getNtcIdx());
        return notices.map(
                        notice -> {
                            notice.setNtcStatus(noticeStatusApiRequest.getNtcStatus());
                            return notice;
                        }).map(notice -> baseRepository.save(notice))
                .map(notice -> response(notice))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );


    }


}
