package com.watcha.watchapedia.model.entity;

import com.watcha.watchapedia.model.config.Auditable;
import com.watcha.watchapedia.model.config.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="tbNotice")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ntcIdx;
    private String ntcTitle;
    private String ntcText;
    private String ntcRegBy;
    private String ntcStatus;
    private String ntcImagepath;
    private String ntcBtnColor;
    private String ntcBtnText;
    private String ntcBtnLink;
}
