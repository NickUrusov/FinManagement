package edu.innotech.services;

import edu.innotech.model.Report;
import edu.innotech.model.TypeTransaction;
import edu.innotech.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public Report get(Long reportId) {
        return reportRepository.findByReportId(reportId);
    }

    public List<Report> get(Long userId
                        , Date startDate
                        , Date endDate){
              return reportRepository.findByUserId(userId, startDate, endDate);
    }

    private void saveReport(Report report
                       , Long userId
                       , Date startDate
                       , Date endDate
                       , BigDecimal saldoIn
                       , BigDecimal saldoOut
                       ){
        report.setUserId(userId);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setSaldoIn(saldoIn);
        report.setSaldoOut(saldoOut);
        reportRepository.save(report);
    }

    public Report post(Long userId, Date startDate, Date endDate){
        Report report = new Report();
        BigDecimal saldoIn  = reportRepository.calculateSum(userId, startDate, endDate, TypeTransaction.CREDITING);
        BigDecimal saldoOut  = reportRepository.calculateSum(userId, startDate, endDate, TypeTransaction.DEBITING);
        saveReport(report, userId, startDate, endDate, saldoIn, saldoOut);
        return report;
    }

    public void delete(Long reportId){
        reportRepository.deleteById(reportId);
    }

}
