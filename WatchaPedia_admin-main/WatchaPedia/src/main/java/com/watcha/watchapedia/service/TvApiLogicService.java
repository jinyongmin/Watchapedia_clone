package com.watcha.watchapedia.service;


import com.watcha.watchapedia.model.entity.Tv;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.TvApiRequest;
import com.watcha.watchapedia.model.network.response.TvApiResponse;
import com.watcha.watchapedia.model.repository.TvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TvApiLogicService extends BaseService<TvApiRequest, TvApiResponse, Tv>{
    private final TvRepository tvRepository;

    private TvApiResponse response(Tv tv) {
        TvApiResponse tvApiResponse = TvApiResponse.builder()
                .tvIdx(tv.getTvIdx())
                .tvAge(tv.getTvAge())
                .tvBackImg(tv.getTvBackImg())
                .tvChannel(tv.getTvChannel())
                .tvCountry(tv.getTvCountry())
                .tvGallery(tv.getTvGallery())
                .tvGenre(tv.getTvGenre())
                .tvTitle(tv.getTvTitle())
                .tvTitleOrg(tv.getTvTitleOrg())
                .tvPeople(tv.getTvPeople())
                .tvSummary(tv.getTvSummary())
                .tvVideo(tv.getTvVideo())
                .tvThumbnail(tv.getTvThumbnail())
                .tvMakingDate(tv.getTvMakingDate())
                .tvWatch(tv.getTvWatch())
                .build();
        return tvApiResponse;
    }

    @Override
    public Header<TvApiResponse> create(Header<TvApiRequest> request) {
        TvApiRequest tvApiRequest = request.getData();
        Tv tv = Tv.builder()
                .tvIdx(tvApiRequest.getTvIdx())
                .tvAge(tvApiRequest.getTvAge())
                .tvBackImg(tvApiRequest.getTvBackImg())
                .tvChannel(tvApiRequest.getTvChannel())
                .tvCountry(tvApiRequest.getTvCountry())
                .tvGallery(tvApiRequest.getTvGallery())
                .tvGenre(tvApiRequest.getTvGenre())
                .tvTitle(tvApiRequest.getTvTitle())
                .tvTitleOrg(tvApiRequest.getTvTitleOrg())
                .tvPeople(tvApiRequest.getTvPeople())
                .tvSummary(tvApiRequest.getTvSummary())
                .tvVideo(tvApiRequest.getTvVideo())
                .tvThumbnail(tvApiRequest.getTvThumbnail())
                .tvMakingDate(tvApiRequest.getTvMakingDate())
                .tvWatch(tvApiRequest.getTvWatch())
                .build();

        Tv newTv = tvRepository.save(tv);
        return Header.OK(response(newTv));
    }



    @Override
    public Header<TvApiResponse> read(Long id) {
        return tvRepository.findById(id).map(tv -> response(tv))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<TvApiResponse> update(Header<TvApiRequest> request) {
        TvApiRequest tvApiRequest = request.getData();
        Optional<Tv> tvs= tvRepository.findByTvIdx(tvApiRequest.getTvIdx());
        System.out.println(tvs);
        return tvs.map(
                        tv -> {
                            if(tvApiRequest.getTvChannel()!=null){tv.setTvChannel((tvApiRequest.getTvChannel()));}
                            tv.setTvGenre(tvApiRequest.getTvGenre());
                            tv.setTvAge(tvApiRequest.getTvAge());
                            if(tvApiRequest.getTvGallery()!=null){tv.setTvGallery(tvApiRequest.getTvGallery());}
                            tv.setTvCountry(tvApiRequest.getTvCountry());
                            tv.setTvIdx(tvApiRequest.getTvIdx());
                            if(tvApiRequest.getTvPeople()!=null){tv.setTvPeople(tvApiRequest.getTvPeople());}
                            if(tvApiRequest.getTvSummary()!=null){tv.setTvSummary(tvApiRequest.getTvSummary());}
                            tv.setTvTitle(tvApiRequest.getTvTitle());
                            if(tvApiRequest.getTvThumbnail()!=null){tv.setTvThumbnail(tvApiRequest.getTvThumbnail());}
                            if(tvApiRequest.getTvBackImg()!=null){tv.setTvBackImg(tvApiRequest.getTvBackImg());}
                            if(tvApiRequest.getTvVideo()!=null){tv.setTvVideo(tvApiRequest.getTvVideo());}
                            if(tvApiRequest.getTvWatch()!=null){tv.setTvWatch(tvApiRequest.getTvWatch());}
                            if(tvApiRequest.getTvMakingDate()!=null){tv.setTvMakingDate(tvApiRequest.getTvMakingDate());}
                            return tv;
                        }).map(tv -> tvRepository.save(tv))
                .map(tv -> response(tv))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );
    }
    @Override
    public Header delete(Long tvIdx) {
        Optional<Tv> tv = tvRepository.findById(tvIdx);
        return tv.map(tv1 -> {
            tvRepository.delete(tv1);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }
    public Header<List<TvApiResponse>> search(){
        List<Tv> tvs = tvRepository.findAll();
        List<TvApiResponse> tvApiResponse = tvs.stream().map(
                tv -> response(tv)).collect(Collectors.toList());
        return Header.OK(tvApiResponse);
    }
    public List<Tv> tvList(){
        return tvRepository.findAll();
    }
}