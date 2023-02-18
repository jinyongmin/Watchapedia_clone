package com.watcha.watchapedia.service;


import com.watcha.watchapedia.model.entity.Webtoon;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.WebtoonApiRequest;
import com.watcha.watchapedia.model.network.response.WebtoonApiResponse;
import com.watcha.watchapedia.model.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebtoonApiLogicService extends BaseService<WebtoonApiRequest, WebtoonApiResponse, Webtoon>{
    private final WebtoonRepository webtoonRepository;

    private WebtoonApiResponse response(Webtoon webtoon) {
        WebtoonApiResponse webtoonApiResponse = WebtoonApiResponse.builder()
                .webIdx(webtoon.getWebIdx())
                .webThumbnail(webtoon.getWebThumbnail())
                .webTitle(webtoon.getWebTitle())
                .webTitleOrg(webtoon.getWebTitleOrg())
                .webWriter(webtoon.getWebWriter())
                .webGenre(webtoon.getWebGenre())
                .webSerDetail(webtoon.getWebSerDetail())
                .webSerDay(webtoon.getWebSerDay())
                .webSerPeriod(webtoon.getWebSerPeriod())
                .webAge(webtoon.getWebAge())
                .webSummary(webtoon.getWebSummary())
                .webPeople(webtoon.getWebPeople())
                .webWatch(webtoon.getWebWatch())
                .webBackImg(webtoon.getWebBackImg())
                .build();
        return webtoonApiResponse;
    }

    @Override
    public Header<WebtoonApiResponse> create(Header<WebtoonApiRequest> request) {
        WebtoonApiRequest webtoonApiRequest = request.getData();
        Webtoon webtoon = Webtoon.builder()
                .webIdx(webtoonApiRequest.getWebIdx())
                .webThumbnail(webtoonApiRequest.getWebThumbnail())
                .webTitle(webtoonApiRequest.getWebTitle())
                .webTitleOrg(webtoonApiRequest.getWebTitleOrg())
                .webWriter(webtoonApiRequest.getWebWriter())
                .webGenre(webtoonApiRequest.getWebGenre())
                .webSerDetail(webtoonApiRequest.getWebSerDetail())
                .webSerDay(webtoonApiRequest.getWebSerDay())
                .webSerPeriod(webtoonApiRequest.getWebSerPeriod())
                .webAge(webtoonApiRequest.getWebAge())
                .webSummary(webtoonApiRequest.getWebSummary())
                .webPeople(webtoonApiRequest.getWebPeople())
                .webWatch(webtoonApiRequest.getWebWatch())
                .webBackImg(webtoonApiRequest.getWebBackImg())
                .build();

        Webtoon newWebtoon = baseRepository.save(webtoon);
        return Header.OK(response(newWebtoon));
    }



    @Override
    public Header<WebtoonApiResponse> read(Long id) {
        return baseRepository.findById(id).map(webtoon -> response(webtoon))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<WebtoonApiResponse> update(Header<WebtoonApiRequest> request) {
        WebtoonApiRequest webtoonApiRequest = request.getData();
        Optional<Webtoon> webtoons= webtoonRepository.findByWebIdx(webtoonApiRequest.getWebIdx());
        System.out.println(webtoons);
        return webtoons.map(
                        webtoon -> {
                            if(webtoonApiRequest.getWebThumbnail()!=null){webtoon.setWebThumbnail(webtoonApiRequest.getWebThumbnail());}
                            webtoon.setWebTitle(webtoonApiRequest.getWebTitle());
                            webtoon.setWebTitleOrg(webtoonApiRequest.getWebTitleOrg());
                            webtoon.setWebWriter(webtoonApiRequest.getWebWriter());
                            webtoon.setWebGenre(webtoonApiRequest.getWebGenre());
                            if(webtoonApiRequest.getWebSerDetail()!=null){webtoon.setWebSerDetail(webtoonApiRequest.getWebSerDetail());}
                            if(webtoonApiRequest.getWebSerDay()!=null){webtoon.setWebSerDay(webtoonApiRequest.getWebSerDay());}
                            if(webtoonApiRequest.getWebSerPeriod()!=null){webtoon.setWebSerPeriod(webtoonApiRequest.getWebSerPeriod());}
                            if(webtoonApiRequest.getWebAge()!=null){webtoon.setWebAge(webtoonApiRequest.getWebAge());}
                            if(webtoonApiRequest.getWebSummary()!=null){webtoon.setWebSummary(webtoonApiRequest.getWebSummary());}
                            if(webtoonApiRequest.getWebPeople()!=null){webtoon.setWebPeople(webtoonApiRequest.getWebPeople());}
                            if(webtoonApiRequest.getWebWatch()!=null){webtoon.setWebWatch(webtoonApiRequest.getWebWatch());}
                            if(webtoonApiRequest.getWebBackImg()!=null){webtoon.setWebBackImg(webtoonApiRequest.getWebBackImg());}
                            return webtoon;
                        }).map(webtoon -> webtoonRepository.save(webtoon))
                .map(webtoon -> response(webtoon))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );
    }
    @Override
    public Header delete(Long webIdx) {
        Optional<Webtoon> webtoon = baseRepository.findById(webIdx);
        return webtoon.map(webtoon1 -> {
            baseRepository.delete(webtoon1);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }
    public Header<List<WebtoonApiResponse>> search(){
        List<Webtoon> webtoons = baseRepository.findAll();
        List<WebtoonApiResponse> webtoonApiResponse = webtoons.stream().map(
                webtoon -> response(webtoon)).collect(Collectors.toList());
        return Header.OK(webtoonApiResponse);
    }
    public List<Webtoon> webtoonList(){
        return webtoonRepository.findAll();
    }
}