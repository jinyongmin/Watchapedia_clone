package com.watcha.watchapedia.model.network.response;

import com.watcha.watchapedia.model.entity.Report;
import com.watcha.watchapedia.model.entity.User;

import java.time.LocalDateTime;

public record ReportDetailDto(
        Long reportIdx,
        User user,
        String reportText,
        String status,
        String answerDate,
        String answerAdminId,
        LocalDateTime reportRegDate

) {
    public static ReportDetailDto of(Long reportIdx,
                                     User user,
                                     String reportText,
                                     String status,
                                     String answerDate,
                                     String answerAdminId,
                                     LocalDateTime reportRegDate){
        return new ReportDetailDto(reportIdx, user, reportText, status, answerDate, answerAdminId, reportRegDate);
    }

    public static ReportDetailDto from(Report report){
        String[] str = report.getReportProcessing().split(",");
        if(str.length > 1){
            return new ReportDetailDto(
                    report.getReportIdx(),
                    report.getUser(),
                    report.getReportText(),
                    str[0],
                    str[1],
                    str[2],
                    report.getReportRegDate()
            );
        }else {
            return new ReportDetailDto(
                    report.getReportIdx(),
                    report.getUser(),
                    report.getReportText(),
                    report.getReportProcessing(),
                    null,
                    null,
                    report.getReportRegDate()
            );
        }


    }




}
