package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.Advertise;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.AdvertiseApiRequest;
import com.watcha.watchapedia.model.network.request.AdvertiseStatusApiRequest;
import com.watcha.watchapedia.model.network.response.AdvertiseApiResponse;
import com.watcha.watchapedia.service.AdvertiseApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/advertise")
@RequiredArgsConstructor
public class AdvertiseApiController extends CrudController<AdvertiseApiRequest, AdvertiseApiResponse, Advertise> {
    private final AdvertiseApiLogicService advertiseApiLogicService;

    @Override
    @PostMapping("")
    public Header<AdvertiseApiResponse> create(@RequestBody Header<AdvertiseApiRequest> request) {
        System.out.println(request.getData());
        return advertiseApiLogicService.create(request);

    }

    @Override
    @GetMapping("{ntcIdx}")
    public Header<AdvertiseApiResponse> read(@PathVariable(name="ntcIdx") Long ntcIdx) {
        return advertiseApiLogicService.read(ntcIdx);
    }


    @Override
    @PutMapping("")  
    public Header<AdvertiseApiResponse> update(@RequestBody Header<AdvertiseApiRequest> request) {
        return advertiseApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{adIdx}")
    public Header<AdvertiseApiResponse> delete(@PathVariable(name="adIdx") Long adIdx) {
        return advertiseApiLogicService.delete(adIdx);
    }


    @PutMapping("/statusupdate")  
    public Header<AdvertiseApiResponse> statusupdate(@RequestBody Header<AdvertiseStatusApiRequest> request) {
        System.out.println("잘들어옴");
        System.out.println(request.getData());
        return advertiseApiLogicService.statusupdate(request);
    }

}
