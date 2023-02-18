package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.*;
import com.watcha.watchapedia.model.network.response.CommentResponse;
import com.watcha.watchapedia.model.network.response.PersonApiResponse;
import com.watcha.watchapedia.model.repository.BookRepository;
import com.watcha.watchapedia.model.repository.MovieRepository;
import com.watcha.watchapedia.model.repository.TvRepository;
import com.watcha.watchapedia.model.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GlobalMethodService {

    private final MovieApiLogicService movieApiLogicService;
    private final TvApiLogicService tvApiLogicService;
    private final BookApiLogicService bookApiLogicService;
    private final WebtoonApiLogicService webtoonApiLogicService;

    @Autowired
    private final MovieRepository movieRepository;

    @Autowired
    private final TvRepository tvRepository;

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final WebtoonRepository webtoonRepository;

    public PersonApiResponse serachResponse(Person person){

        System.out.println(person.getPerName() + "의 정보를 가지고 stream -> map -> response메소드에 진입 성공!");
        //출연 및 연출작 List를 넘겨주기 위한 List 제작 과정(2개)

        List<String> appearMov = new ArrayList<>();
        List<String> appearTv = new ArrayList<>();
        List<String> appearBook = new ArrayList<>();
        List<String> appearWeb = new ArrayList<>();

        List<String> appearFinal = new ArrayList<>();

        //영화 연출작 "," 기준으로 스플릿해서  appearMov에 담기
        if (person.getPerMov() != null) {
            String[] movArr = person.getPerMov().split(",");
            for(String s : movArr){
                appearMov.add(s);
            }
        }

        //Tv 연출작 "," 기준으로 스플릿해서  appearTv에 담기
        if(person.getPerTv() != null){
            String[] tvArr = person.getPerTv().split(",");
            for(String s : tvArr){
                appearTv.add(s);
            }
        }

        //Book 연출작 ","기준으로 스플릿해서 appearBook에 담기
        if(person.getPerBook() != null){
            String[] bookArr = person.getPerBook().split(",");
            for(String s : bookArr){
                appearBook.add(s);
            }
        }

        //Webtoon 연출작 ","기준으로 스플릿해서 appearWeb에 담기
        if(person.getPerWebtoon() != null){
            String[] webArr = person.getPerWebtoon().split(",");
            for(String s : webArr){
                appearWeb.add(s);
            }
        }

        //영화 연출작, TV 연출작, ... 연출작 갯수 확인
        System.out.println("appearMov.size() : " + appearMov.size());
        System.out.println("appearTv.size() : " + appearTv.size());
        System.out.println("appearBook.size() : " + appearBook.size());
        System.out.println("appearWeb.size() : " + appearWeb.size());


        //appearFinal에 담아주기
        for(String s : appearMov){
            if(appearFinal.size() >= 2){
                break;
            }
            appearFinal.add(movieApiLogicService.read(Long.valueOf(s)).getData().getMovTitle());
        }

        for(String s : appearTv){
            if(appearFinal.size() >= 2){
                break;
            }
            appearFinal.add(tvApiLogicService.read(Long.valueOf(s)).getData().getTvTitle());
        }

        for(String s : appearBook){
            if(appearFinal.size() >= 2){
                break;
            }
            appearFinal.add(bookApiLogicService.read(Long.valueOf(s)).getData().getBookTitle());
        }

        for(String s : appearWeb){
            if(appearFinal.size() >= 2){
                break;
            }
            appearFinal.add(webtoonApiLogicService.read(Long.valueOf(s)).getData().getWebTitle());
        }

        //연출작이 3개가 넘으면 2개만 남기고 지워줌
        while(appearFinal.size() >= 3){
            appearFinal.remove(appearFinal.size()-1);
        }

        //인물사진이 없으면 기본이미지 넣기
        String personSphoto = person.getPerPhoto();
        if(personSphoto == null){
            personSphoto = "data:image/jpeg;base64,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";
        }

        //PersonApiResponse형으로 Person을 변형하는 과정
        PersonApiResponse personApiResponse = PersonApiResponse.builder()
                .perIdx(person.getPerIdx())
                .perName(person.getPerName())
                .perPhoto(personSphoto)
                .perRole(person.getPerRole())
                .appearance(appearFinal)
                .build();
        System.out.println("PersonApiResponse형태로 뽑아낸 결과 : " + personApiResponse);
        return personApiResponse;
    }

    //코멘트 상세보기 페이지에서 사용되는 포스터 src주소 + 해당 코멘트의 컨텐츠 제목을 리턴하는 메소드
    // 매개변수 = CommentResponse
    // 0번째 인덱스 리턴 = postUrl
    // 1번째 인덱스 리턴 = title
    public List<String> getPostUrlAndTitle(CommentResponse commentResponse){

        //리턴해줄 List<String>
        List<String> str = new ArrayList<>();

        String posterUrl = "";
        String title = "";

        switch (commentResponse.commContentType()){
            case "movie":
                //select문을 한 번 찾아서 변수 2개 추출
                Optional<Movie> movie = movieRepository.findByMovIdx(commentResponse.commContentIdx());
                title = movie.get().getMovTitle();
                posterUrl = movie.get().getMovThumbnail();

                break;
            case "tv":
                Optional<Tv> tv = tvRepository.findByTvIdx(commentResponse.commContentIdx());
                title = tv.get().getTvTitle();
                posterUrl = tv.get().getTvThumbnail();
                break;
            case "book":
                //select문을 한 번 찾아서 변수 2개 추출
                Optional<Book> book = bookRepository.findByBookIdx(commentResponse.commContentIdx());
                title = book.get().getBookTitle();
                posterUrl = book.get().getBookThumbnail();
                break;
            case "webtoon","웹툰":
                //select문을 한 번 찾아서 변수 2개 추출
                Optional<Webtoon> webtoon = webtoonRepository.findByWebIdx(commentResponse.commContentIdx());
                title = webtoon.get().getWebTitle();
                posterUrl = webtoon.get().getWebThumbnail();
        }

        str.add(posterUrl);
        str.add(title);

        return str;
    }

    public List<CommentResponse> getListTitle(List<CommentResponse> comments){

        List<CommentResponse> data = new ArrayList<>();

        for(CommentResponse c : comments){
            switch (c.commContentType()){
                case "movie":
                    String movieTitle = movieRepository.findTitleByMovIdx(c.commContentIdx()).getMovTitle();
                    data.add(c.insertTitle(c, movieTitle));
                    break;
                case "tv":
                    String tvTitle = tvRepository.findTitleByTvIdx(c.commContentIdx()).getTvTitle();
                    data.add(c.insertTitle(c, tvTitle));
                    break;
                case "book":
                    String bookTitle = bookRepository.findTitleByBookIdx(c.commContentIdx()).getBookTitle();
                    data.add(c.insertTitle(c, bookTitle));
                    break;
                case "webtoon","웹툰":
                    String webtoonTitle = webtoonRepository.findTitleByWebIdx(c.commContentIdx()).getWebTitle();
                    data.add(c.insertTitle(c, webtoonTitle));
            }

        }
        return data;
    }

    public List<Recomment> getComment(){

        return null;
    }




}
