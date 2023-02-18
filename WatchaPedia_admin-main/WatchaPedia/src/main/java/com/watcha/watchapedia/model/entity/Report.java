package com.watcha.watchapedia.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tbReport")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportIdx;
    @Setter @ManyToOne @JoinColumn(name = "report_user_idx")private User user;
    @Setter @Column(length = 5) private String reportCommType;
    @Setter private Long reportCommIdx;
    @Setter private String reportText;
    @Setter @Column private Long reportSpoiler;
    @Setter private Long reportInappropriate;
    @Setter @Column(length = 30) private String reportProcessing;
    @Setter private String reportReporter;
    @Setter @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime reportRegDate;
}
