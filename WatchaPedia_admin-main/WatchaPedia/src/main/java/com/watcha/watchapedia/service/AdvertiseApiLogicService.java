package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.Advertise;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.AdvertiseApiRequest;
import com.watcha.watchapedia.model.network.request.AdvertiseStatusApiRequest;
import com.watcha.watchapedia.model.network.response.AdvertiseApiResponse;
import com.watcha.watchapedia.model.repository.AdvertiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertiseApiLogicService extends BaseService<AdvertiseApiRequest, AdvertiseApiResponse, Advertise> {

    private final AdvertiseRepository advertiseRepository;

    private AdvertiseApiResponse response(Advertise advertise) {
        AdvertiseApiResponse advertiseApiResponse = AdvertiseApiResponse.builder()
                .adIdx(advertise.getAdIdx())
                .adTitle(advertise.getAdTitle())
                .adDescription(advertise.getAdDescription())
                .adStatus(advertise.getAdStatus())
                .adVideosource(advertise.getAdVideosource())
                .adImagesource(advertise.getAdImagesource())
                .adBtnColor(advertise.getAdBtnColor())
                .adBtnLink(advertise.getAdBtnLink())
                .adBtnText(advertise.getAdBtnText())
                .adClient(advertise.getAdClient())
                .adClientLogoimage(advertise.getAdClientLogoimage())
                .endDate(advertise.getEndDate())
                .build();
        return advertiseApiResponse;
    }

    @Override
    public Header<AdvertiseApiResponse> create(Header<AdvertiseApiRequest> request) {
        AdvertiseApiRequest advertiseApiRequest = request.getData();

        System.out.println(advertiseApiRequest);

        Advertise tbAdvertise = Advertise.builder()
                .adTitle(advertiseApiRequest.getAdTitle())
                .adDescription(advertiseApiRequest.getAdDescription())
                .adStatus(advertiseApiRequest.getAdStatus())
                .adVideosource(advertiseApiRequest.getAdVideosource())
                .adImagesource(advertiseApiRequest.getAdImagesource())
                .adBtnColor(advertiseApiRequest.getAdBtnColor())
                .adBtnLink(advertiseApiRequest.getAdBtnLink())
                .adBtnText(advertiseApiRequest.getAdBtnText())
                .adClient(advertiseApiRequest.getAdClient())
                .adClientLogoimage(advertiseApiRequest.getAdClientLogoimage())
                .endDate(advertiseApiRequest.getEndDate())
                .build();
        Advertise newTbAdvertise = advertiseRepository.save(tbAdvertise);

        return Header.OK(response(newTbAdvertise));
    }

    @Override
    public Header<AdvertiseApiResponse> read(Long id) {
        return baseRepository.findById(id).map(ad -> response(ad))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<AdvertiseApiResponse> update(Header<AdvertiseApiRequest> request) {
        AdvertiseApiRequest advertiseApiRequest = request.getData();
        Optional<Advertise> advertises = advertiseRepository.findById(advertiseApiRequest.getAdIdx());
        return advertises.map(
                        ad -> {
                            ad.setAdTitle(advertiseApiRequest.getAdTitle());
                            ad.setAdDescription(advertiseApiRequest.getAdDescription());
                            ad.setAdStatus(advertiseApiRequest.getAdStatus());
                            ad.setAdVideosource(advertiseApiRequest.getAdVideosource());
                            if(advertiseApiRequest.getAdImagesource()!=null){ad.setAdImagesource(advertiseApiRequest.getAdImagesource());}
                            if(advertiseApiRequest.getAdBtnColor()!=null){ad.setAdBtnColor(advertiseApiRequest.getAdBtnColor());}
                            ad.setAdBtnLink(advertiseApiRequest.getAdBtnLink());
                            ad.setAdBtnText(advertiseApiRequest.getAdBtnText());
                            ad.setAdClient(advertiseApiRequest.getAdClient());
                            if(advertiseApiRequest.getAdClientLogoimage()!=null){ad.setAdClientLogoimage(advertiseApiRequest.getAdClientLogoimage());}
                            if(advertiseApiRequest.getEndDate()!=null){ad.setEndDate(advertiseApiRequest.getEndDate());}
                            return ad;
                        }).map(ad -> advertiseRepository.save(ad))
                .map(ad -> response(ad))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );
    }


    @Override
    public Header delete(Long adIdx) {
        Optional<Advertise> advertise = baseRepository.findById(adIdx);
        return advertise.map(advertise1 -> {
            baseRepository.delete(advertise1);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }


    public Header<AdvertiseApiResponse> statusupdate(Header<AdvertiseStatusApiRequest> request) {
        AdvertiseStatusApiRequest advertiseStatusApiRequest = request.getData();
        Optional<Advertise> advertises = advertiseRepository.findById(advertiseStatusApiRequest.getAdIdx());
        return advertises.map(
                        ad -> {
                            ad.setAdStatus(advertiseStatusApiRequest.getAdStatus());
                            return ad;
                        }).map(ad -> baseRepository.save(ad))
                .map(ad -> response(ad))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );


    }

    public List<Advertise> advertiseList(){
        return advertiseRepository.findAll();
    }
}
