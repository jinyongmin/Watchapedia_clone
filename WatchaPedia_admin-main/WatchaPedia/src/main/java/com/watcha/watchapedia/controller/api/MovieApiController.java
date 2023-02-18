package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.Movie;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.MovieApiRequest;
import com.watcha.watchapedia.model.network.response.MovieApiResponse;
import com.watcha.watchapedia.model.network.response.PersonApiResponse;
import com.watcha.watchapedia.service.MovieApiLogicService;
import com.watcha.watchapedia.service.PersonApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieApiController extends CrudController<MovieApiRequest, MovieApiResponse, Movie> {
    private final MovieApiLogicService movieApiLogicService;

    @PostMapping("") // http://localhost:8888/api/movie (post)
    public Header<List<MovieApiResponse>> creat(@RequestBody Header<MovieApiRequest> request) {
        System.out.println("apimovie create reqeustgetdata"+request.getData());
        return movieApiLogicService.cr(request);
    }

    @Override
    @GetMapping("{movIdx}") // http://localhost:8888/api/movie/{movIdx} (get)
    public Header<MovieApiResponse> read(@PathVariable(name="movIdx") Long movIdx) {
        return movieApiLogicService.read(movIdx);
    }

    @GetMapping("") // http://localhost:8888/api/movie
    public Header<List<MovieApiResponse>> findAll(){
        return movieApiLogicService.search();
    }

    @Override
    @PutMapping("") // http://localhost:8888/api/movie (put)
    public Header<MovieApiResponse> update(@RequestBody Header<MovieApiRequest> request) {
        return movieApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}") // http://localhost:8888/api/movie/{id} (delete)
    public Header<MovieApiResponse> delete(@PathVariable Long id) {
        return movieApiLogicService.delete(id);
    }

    @Autowired
    private final PersonApiLogicService personApiLogicService;

    @GetMapping("/searchKey")
    public Header<List<PersonApiResponse>> searchPerson(@RequestParam("searchKey") String searchKey){
        System.out.println("searchPerson까지는 잘 들어옴");
        return personApiLogicService.search(searchKey);
    }
}


