package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.dto.ReportDto;
import com.watcha.watchapedia.model.entity.Report;
import com.watcha.watchapedia.model.entity.Spoiler;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.repository.ReportRepository;
import com.watcha.watchapedia.model.repository.SpoilerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportApiLogicService {
    private final ReportRepository reportRepository;

    private final SpoilerRepository spoilerRepository;

    public List<ReportDto> findAllReport(){
        return reportRepository.findAll().stream().map(ReportDto::from).toList();
    }

    public Header updateReport(Long reportIdx, String updateStatus, String processAdmin, String updateDate){
        try{
            //report 상태 변경
            Report report = reportRepository.getReferenceById(reportIdx);
            System.out.println(report);
            report.setReportProcessing(updateStatus + "," + updateDate + "," + processAdmin);
            System.out.println(report);
            reportRepository.save(report);

            //
            Optional<Spoiler> nullcheck = spoilerRepository.findBySpoCommentIdx(report.getReportCommIdx());
            if(nullcheck.isPresent()){
                //이미 스포일러 신고가 되어있으면 delete
                spoilerRepository.delete(nullcheck.get());
            }else{
                //spoiler가림막 씌우기 위해 tb_spoiler에 commentIdx 전달 후 save
                spoilerRepository.save(Spoiler.of(report.getReportCommIdx()));
            }



            return Header.OK(report);
        }catch(EntityNotFoundException e){
            System.out.println("해당하는 report가 없어요;;");
            return Header.ERROR();
        }

    }
}
