package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.Book;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.BookApiRequest;
import com.watcha.watchapedia.model.network.response.BookApiResponse;
import com.watcha.watchapedia.model.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookApiLogicService extends BaseService<BookApiRequest, BookApiResponse, Book>{
    private final BookRepository bookRepository;

    private BookApiResponse response(Book book){
        BookApiResponse bookApiResponse = BookApiResponse.builder()
                .bookIdx(book.getBookIdx())
                .bookTitle(book.getBookTitle())
                .bookTitleSub(book.getBookTitleSub())
                .bookWriter(book.getBookWriter())
                .bookCategory(book.getBookCategory())
                .bookAtDate(book.getBookAtDate())
                .bookPage(book.getBookPage())
                .bookAge(book.getBookAge())
                .bookSummary(book.getBookSummary())
                .bookPeople(book.getBookPeople())
                .bookContentIdx(book.getBookContentIdx())
                .bookPubSummary(book.getBookPubSummary())
                .bookThumbnail(book.getBookThumbnail())
                .bookBackImg(book.getBookBackImg())
                .bookBuy(book.getBookBuy())
                .build();
        return bookApiResponse;
    }

    @Override
    public Header<BookApiResponse> create(Header<BookApiRequest> request) {
        BookApiRequest bookApiRequest = request.getData();
        Book book = Book.builder()
                .bookTitle(bookApiRequest.getBookTitle())
                .bookTitleSub(bookApiRequest.getBookTitleSub())
                .bookWriter(bookApiRequest.getBookWriter())
                .bookCategory(bookApiRequest.getBookCategory())
                .bookAtDate(bookApiRequest.getBookAtDate())
                .bookPage(bookApiRequest.getBookPage())
                .bookAge(bookApiRequest.getBookAge())
                .bookSummary(bookApiRequest.getBookSummary())
                .bookPeople(bookApiRequest.getBookPeople())
                .bookContentIdx(bookApiRequest.getBookContentIdx())
                .bookPubSummary(bookApiRequest.getBookPubSummary())
                .bookThumbnail(bookApiRequest.getBookThumbnail())
                .bookBackImg(bookApiRequest.getBookBackImg())
                .bookBuy(bookApiRequest.getBookBuy())
                .build();
        Book newBook = baseRepository.save(book);
        return Header.OK(response(newBook));
    }



    @Override
    public Header<BookApiResponse> read(Long id) {
        return baseRepository.findById(id).map(book -> response(book))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<BookApiResponse> update(Header<BookApiRequest> request) {
        BookApiRequest bookApiRequest = request.getData();
        Optional<Book> books = bookRepository.findByBookIdx(bookApiRequest.getBookIdx());
        System.out.println(books);
        return books.map(
                        book -> {
                            book.setBookTitle(bookApiRequest.getBookTitle());
                            book.setBookTitleSub(bookApiRequest.getBookTitleSub());
                            book.setBookWriter(bookApiRequest.getBookWriter());
                            book.setBookCategory(bookApiRequest.getBookCategory());
                            book.setBookAtDate(bookApiRequest.getBookAtDate());
                            book.setBookPage(bookApiRequest.getBookPage());
                            book.setBookAge(bookApiRequest.getBookAge());
                            book.setBookSummary(bookApiRequest.getBookSummary());
                            if(bookApiRequest.getBookPeople()!=null){book.setBookPeople(bookApiRequest.getBookPeople());}
                            if(bookApiRequest.getBookContentIdx()!=null){book.setBookContentIdx(bookApiRequest.getBookContentIdx());}
                            if(bookApiRequest.getBookPubSummary()!=null){book.setBookPubSummary(bookApiRequest.getBookPubSummary());}
                            if(bookApiRequest.getBookThumbnail()!=null){book.setBookThumbnail(bookApiRequest.getBookThumbnail());}
                            if(bookApiRequest.getBookBackImg()!=null){book.setBookBackImg(bookApiRequest.getBookBackImg());}
                            if(bookApiRequest.getBookBuy()!=null){book.setBookBuy(bookApiRequest.getBookBuy());}
                            return book;
                        }).map(book -> bookRepository.save(book))
                .map(book -> response(book))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header delete(Long movIdx) {
        Optional<Book> book = baseRepository.findById(movIdx);
        return book.map(book1 -> {
            baseRepository.delete(book1);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }


    public Header<List<BookApiResponse>> search(){
        List<Book> books = baseRepository.findAll();
        List<BookApiResponse> bookApiResponse = books.stream().map(
                book -> response(book)).collect(Collectors.toList());


        return Header.OK(bookApiResponse);
    }


    public List<Book> bookList(){
        return bookRepository.findAll();
    }
}

