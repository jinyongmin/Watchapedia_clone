package com.watcha.watchapedia.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PersonApiRequest {
    private Long perIdx;
//    private String perName;
//    private String perPhoto;
//    private String perRole;
//    private List<String> appearance;
}
