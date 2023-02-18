package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.Tv;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.TvApiRequest;
import com.watcha.watchapedia.model.network.response.TvApiResponse;
import com.watcha.watchapedia.service.TvApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tv")
@RequiredArgsConstructor
public class TvApiController extends CrudController<TvApiRequest, TvApiResponse, Tv> {
    private final TvApiLogicService tvApiLogicService;
    @Override
    @PostMapping("") // http://localhost:8888/api/tv (post)
    public Header<TvApiResponse> create(@RequestBody Header<TvApiRequest> request) {
        System.out.println(request.getData());
        return tvApiLogicService.create(request);
    }

    @Override
    @GetMapping("{tvIdx}") // http://localhost:8888/api/tv/{tvIdx} (get)
    public Header<TvApiResponse> read(@PathVariable(name="tvIdx") Long tvIdx) {
        return tvApiLogicService.read(tvIdx);
    }

    @GetMapping("") // http://localhost:8888/api/tv
    public Header<List<TvApiResponse>> findAll(){
        return tvApiLogicService.search();
    }

    @Override
    @PutMapping("") // http://localhost:8888/api/yb (put)
    public Header<TvApiResponse> update(@RequestBody Header<TvApiRequest> request) {
        return tvApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}") // http://localhost:8888/api/tv/{id} (delete)
    public Header<TvApiResponse> delete(@PathVariable Long id) {
        return tvApiLogicService.delete(id);
    }
}


