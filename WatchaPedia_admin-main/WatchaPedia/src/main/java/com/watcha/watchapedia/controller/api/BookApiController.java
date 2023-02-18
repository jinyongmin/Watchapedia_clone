package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.Book;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.BookApiRequest;
import com.watcha.watchapedia.model.network.response.BookApiResponse;
import com.watcha.watchapedia.service.BookApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookApiController extends CrudController<BookApiRequest, BookApiResponse, Book> {
    private final BookApiLogicService bookApiLogicService;
    @Override
    @PostMapping("") // http://localhost:8888/api/book (post)
    public Header<BookApiResponse> create(@RequestBody Header<BookApiRequest> request) {
        System.out.println(request.getData());
        return bookApiLogicService.create(request);
    }

    @Override
    @GetMapping("{bookIdx}") // http://localhost:8888/api/book/{bookIdx} (get)
    public Header<BookApiResponse> read(@PathVariable(name="bookIdx") Long bookIdx) {
        return bookApiLogicService.read(bookIdx);
    }

    @GetMapping("") // http://localhost:8888/api/book?page=0
    public Header<List<BookApiResponse>> findAll(){
        return bookApiLogicService.search();
    }

    @Override
    @PutMapping("") // http://localhost:8888/api/book (put)
    public Header<BookApiResponse> update(@RequestBody Header<BookApiRequest> request) {
        return bookApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}") // http://localhost:8888/api/book/{id} (delete)
    public Header<BookApiResponse> delete(@PathVariable Long id) {
        return bookApiLogicService.delete(id);
    }
}


