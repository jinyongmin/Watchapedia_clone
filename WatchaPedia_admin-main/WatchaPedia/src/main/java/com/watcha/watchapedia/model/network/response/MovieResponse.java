package com.watcha.watchapedia.model.network.response;

public record MovieResponse(
        Long idx,
        String title,
        String director,
        String makingDate,
        String genre,
        String country
) {
    public static MovieResponse of(
            Long idx, String title, String director, String makingDate, String genre, String country
    ){
        return new MovieResponse(
                idx,title,director,makingDate,genre,country
        );
    }
}
