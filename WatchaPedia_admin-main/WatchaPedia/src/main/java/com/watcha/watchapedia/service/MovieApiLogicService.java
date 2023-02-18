package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.Movie;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.MovieApiRequest;
import com.watcha.watchapedia.model.network.response.MovieApiResponse;
import com.watcha.watchapedia.model.network.response.MovieResponse;
import com.watcha.watchapedia.model.repository.MovieRepository;
import com.watcha.watchapedia.model.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class MovieApiLogicService extends BaseService<MovieApiRequest, MovieApiResponse, Movie>{
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;


    private MovieApiResponse response(Movie movie){
        MovieApiResponse movieApiResponse = MovieApiResponse.builder()
                .movIdx(movie.getMovIdx())
                .movThumbnail(movie.getMovThumbnail())
                .movTitle(movie.getMovTitle())
                .movTitleOrg(movie.getMovTitleOrg())
                .movMakingDate(movie.getMovMakingDate())
                .movCountry(movie.getMovCountry())
                .movGenre(movie.getMovGenre())
                .movTime(movie.getMovTime())
                .movAge(movie.getMovAge())
                .movPeople(movie.getMovPeople())
                .movSummary(movie.getMovSummary())
                .movGallery(movie.getMovGallery())
                .movVideo(movie.getMovVideo())
                .movWatch(movie.getMovWatch())
                .movBackImg(movie.getMovBackImg())
                .build();
        return movieApiResponse;
    }

    @Override
    public Header<MovieApiResponse> create(Header<MovieApiRequest> request) {
        MovieApiRequest movieApiRequest = request.getData();
        Movie movie = Movie.builder()
                .movThumbnail(movieApiRequest.getMovThumbnail())
                .movTitle(movieApiRequest.getMovTitle())
                .movTitleOrg(movieApiRequest.getMovTitleOrg())
                .movMakingDate(movieApiRequest.getMovMakingDate())
                .movCountry(movieApiRequest.getMovCountry())
                .movGenre(movieApiRequest.getMovGenre())
                .movTime(movieApiRequest.getMovTime())
                .movAge(movieApiRequest.getMovAge())
                .movPeople(movieApiRequest.getMovPeople())
                .movSummary(movieApiRequest.getMovSummary())
                .movGallery(movieApiRequest.getMovGallery())
                .movVideo(movieApiRequest.getMovVideo())
                .movWatch(movieApiRequest.getMovWatch())
                .movBackImg(movieApiRequest.getMovBackImg())
                .build();
        Movie newMovie = baseRepository.save(movie);
        return Header.OK(response(newMovie));
    }

    public Header<List<MovieApiResponse>> cr(Header<MovieApiRequest> request) {
        MovieApiRequest movieApiRequest = request.getData();
        Movie movie = Movie.builder()
                .movThumbnail(movieApiRequest.getMovThumbnail())
                .movTitle(movieApiRequest.getMovTitle())
                .movTitleOrg(movieApiRequest.getMovTitleOrg())
                .movMakingDate(movieApiRequest.getMovMakingDate())
                .movCountry(movieApiRequest.getMovCountry())
                .movGenre(movieApiRequest.getMovGenre())
                .movTime(movieApiRequest.getMovTime())
                .movAge(movieApiRequest.getMovAge())
                .movPeople(movieApiRequest.getMovPeople())
                .movSummary(movieApiRequest.getMovSummary())
                .movGallery(movieApiRequest.getMovGallery())
                .movVideo(movieApiRequest.getMovVideo())
                .movWatch(movieApiRequest.getMovWatch())
                .movBackImg(movieApiRequest.getMovBackImg())
                .build();
        Long newMovie = baseRepository.save(movie).getMovIdx();
        System.out.println("새로등록된 영화의 idx= " + newMovie);

        List<Movie> movies = baseRepository.findById(newMovie).stream().toList();
        List<MovieApiResponse> movieApiResponse = movies.stream().map(
                mov -> response(mov)).collect(Collectors.toList());

        return Header.OK(movieApiResponse);
    }




    @Override
    public Header<MovieApiResponse> read(Long id) {
        return baseRepository.findById(id).map(movie -> response(movie))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<MovieApiResponse> update(Header<MovieApiRequest> request) {
        MovieApiRequest movieApiRequest = request.getData();
        Optional<Movie> movies = movieRepository.findByMovIdx(movieApiRequest.getMovIdx());
        System.out.println(movies);
        return movies.map(
                        movie -> {
                            movie.setMovThumbnail(movieApiRequest.getMovThumbnail());
                            movie.setMovTitle(movieApiRequest.getMovTitle());
                            movie.setMovTitleOrg(movieApiRequest.getMovTitleOrg());
                            movie.setMovMakingDate(movieApiRequest.getMovMakingDate());
                            movie.setMovCountry(movieApiRequest.getMovCountry());
                            movie.setMovGenre(movieApiRequest.getMovGenre());
                            movie.setMovTime(movieApiRequest.getMovTime());
                            movie.setMovAge(movieApiRequest.getMovAge());
                            if(movieApiRequest.getMovPeople()!=null){movie.setMovPeople(movieApiRequest.getMovPeople());}
                            if(movieApiRequest.getMovSummary()!=null){movie.setMovSummary(movieApiRequest.getMovSummary());}
                            if(movieApiRequest.getMovGallery()!=null){movie.setMovGallery(movieApiRequest.getMovGallery());}
                            if(movieApiRequest.getMovVideo()!=null){movie.setMovVideo(movieApiRequest.getMovVideo());}
                            if(movieApiRequest.getMovWatch()!=null){movie.setMovWatch(movieApiRequest.getMovWatch());}
                            if(movieApiRequest.getMovBackImg()!=null){movie.setMovBackImg(movieApiRequest.getMovBackImg());}
                            return movie;
                        }).map(movie -> movieRepository.save(movie))
                .map(movie -> response(movie))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header delete(Long movIdx) {
        Optional<Movie> movie = baseRepository.findById(movIdx);
        return movie.map(movie1 -> {
            baseRepository.delete(movie1);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }


    public Header<List<MovieApiResponse>> search(){
        List<Movie> movies = baseRepository.findAll();
        List<MovieApiResponse> movieApiResponse = movies.stream().map(
                movie -> response(movie)).collect(Collectors.toList());


        return Header.OK(movieApiResponse);
    }


    public List<MovieResponse> movieList(){
        List<MovieResponse> movie = movieRepository.findAll().stream().map(mov->{
            String str = null;
            if(mov.getMovPeople()!=null){
                if(mov.getMovPeople().contains("감독")){
                    str = mov.getMovPeople().split("\\(감독\\)")[0];
                    if(str.contains(",")) str = str.split(",")[1];
                }
            }
            return MovieResponse.of(mov.getMovIdx(),mov.getMovTitle(),
                    str!=null?personRepository.getReferenceById(Long.valueOf(str)).getPerName():""
                    ,mov.getMovMakingDate(),mov.getMovGenre(),mov.getMovCountry()
            );
        }).collect(Collectors.toList());
        return movie;
    }
}

