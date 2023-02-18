package com.watcha.watchapedia.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(
        name = "tbPerson"
)
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perIdx;
    private String perName;
    private String perPhoto;
    private String perRole;
    private String perMov ;
    private String perBook ;
    private String perWebtoon;
    private String perTv;
    private String perBiography;
}
