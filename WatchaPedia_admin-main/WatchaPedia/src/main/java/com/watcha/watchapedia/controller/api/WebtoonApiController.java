package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.Webtoon;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.WebtoonApiRequest;
import com.watcha.watchapedia.model.network.response.WebtoonApiResponse;
import com.watcha.watchapedia.service.WebtoonApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/webtoon")
@RequiredArgsConstructor
public class WebtoonApiController extends CrudController<WebtoonApiRequest, WebtoonApiResponse, Webtoon> {
    private final WebtoonApiLogicService webtoonApiLogicService;
    @Override
    @PostMapping("") // http://localhost:8888/api/webtoon (post)
    public Header<WebtoonApiResponse> create(@RequestBody Header<WebtoonApiRequest> request) {
        System.out.println(request.getData());
        return webtoonApiLogicService.create(request);
    }

    @Override
    @GetMapping("{webtoonIdx}") // http://localhost:8888/api/webtoon/{webtoonIdx} (get)
    public Header<WebtoonApiResponse> read(@PathVariable(name="webtoonIdx") Long webtoonIdx) {
        return webtoonApiLogicService.read(webtoonIdx);
    }

    @GetMapping("") // http://localhost:8888/api/webtoon (get)
    public Header<List<WebtoonApiResponse>> findAll(){
        return webtoonApiLogicService.search();
    }

    @Override
    @PutMapping("") // http://localhost:8888/api/webtoon (put)
    public Header<WebtoonApiResponse> update(@RequestBody Header<WebtoonApiRequest> request) {
        return webtoonApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}") // http://localhost:8888/api/webtoon/{id} (delete)
    public Header<WebtoonApiResponse> delete(@PathVariable Long id) {
        return webtoonApiLogicService.delete(id);
    }
}


