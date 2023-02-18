package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.AdminUser;
import com.watcha.watchapedia.model.entity.Report;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.AdminApiRequest;
import com.watcha.watchapedia.model.network.request.AdminJustIdxRequest;
import com.watcha.watchapedia.model.network.request.ReportApiRequest;
import com.watcha.watchapedia.model.network.response.AdminApiResponse;
import com.watcha.watchapedia.model.network.response.ReportApiResponse;
import com.watcha.watchapedia.service.AdminApiLogicService;
import com.watcha.watchapedia.service.ReportApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/api/report")    // http://localhost:9090/api/report
@RequiredArgsConstructor
public class ReportApiController extends CrudController<ReportApiRequest, ReportApiResponse, Report> {
    private final ReportApiLogicService reportApiLogicService;
    @Override
    public Header<ReportApiResponse> create(@RequestBody Header<ReportApiRequest> request) {
        return null;
    }

    @Override
    public Header<ReportApiResponse> read(Long id) {
        return super.read(id);
    }

    @Override
    @PutMapping("/update")
    public Header<ReportApiResponse> update(@RequestBody Header<ReportApiRequest> request) {


        Long reportIdx = Long.valueOf(request.getData().getReportIdx());
        String updateStatus = request.getData().getUpdateStatus();
        String processAdmin = request.getData().getProcessAdmin();
        String updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
        System.out.println("ReportApiController에 3가지 정보 + 날짜 : " + reportIdx + updateStatus + processAdmin + updateDate);

        return reportApiLogicService.updateReport(reportIdx, updateStatus, processAdmin, updateDate);
    }

    @Override
    public Header<ReportApiResponse> delete(Long id) {
        return null;
    }
}