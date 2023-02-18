package com.watcha.watchapedia.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookApiRequest {
    private Long bookIdx;
    private String bookTitle;
    private String bookTitleSub;
    private String bookWriter;
    private String bookCategory;
    private String bookAtDate;
    private String bookPage;
    private String bookAge;
    private String bookSummary;
    private String bookPeople;
    private String bookContentIdx;
    private String bookPubSummary;
    private String bookThumbnail;
    private String bookBackImg;
    private String bookBuy;
}
