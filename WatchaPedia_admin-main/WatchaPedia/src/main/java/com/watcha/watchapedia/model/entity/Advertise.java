package com.watcha.watchapedia.model.entity;

import com.watcha.watchapedia.model.config.Auditable;
import com.watcha.watchapedia.model.config.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name="tbAdvertise")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Advertise extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adIdx;
    private String adTitle;
    private String adDescription;
    private String adStatus;
    private String adVideosource;
    private String adImagesource;
    private String adBtnLink;
    private String adBtnColor;
    private String adBtnText;
    private String adClient;
    private String adClientLogoimage;
    private String endDate;
}
