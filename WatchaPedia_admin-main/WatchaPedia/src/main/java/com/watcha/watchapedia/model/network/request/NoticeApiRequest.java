package com.watcha.watchapedia.model.network.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NoticeApiRequest {
    private Long ntcIdx;
    private String ntcTitle;
    private String ntcImagepath;
    private String ntcText;
    private String ntcBtnText;
    private String ntcBtnColor;
    private String ntcRegBy;
    private String ntcBtnLink;
}