package edu.innotech.controllers;

import edu.innotech.dto.ReportInstance;
import edu.innotech.dto.ReportResponse;
import edu.innotech.dto.ReportResponseData;
import edu.innotech.model.Report;
import edu.innotech.services.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportController {

    @Autowired
    Environment environment;

    @Autowired
    private ReportService reportService;

    @PostMapping("/reports/")
    public ResponseEntity<ReportResponseData> post(@Valid @RequestBody ReportInstance reportInstance){
        Report report = reportService.post(reportInstance.getUserId()
                                        , reportInstance.getStartDate()
                                        , reportInstance.getEndDate()
                                        );

        ReportResponseData reportResponseData = new ReportResponseData();
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setReportId(report.getId().toString());
        reportResponseData.setData(reportResponse);

        return ResponseEntity.ok(reportResponseData);
    }

    @GetMapping("/reports/{report_id}/")
    public ResponseEntity<ReportResponseData> get(@PathVariable("report_id") Long reportId){
        Report report = reportService.get(reportId);

        if(report == null){
            return new ResponseEntity("Не найден отчет по Id", HttpStatus.NOT_FOUND);
        }

        ReportResponseData reportResponseData = new ReportResponseData();
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setReportId(reportId.toString());
        reportResponse.setUserId(report.getUserId());
        reportResponse.setStartDate(report.getStartDate());
        reportResponse.setEndDate(report.getEndDate());
        reportResponse.setSaldoIn(report.getSaldoIn());
        reportResponse.setSaldoOut(report.getSaldoOut());
        reportResponseData.setData(reportResponse);

        return ResponseEntity.ok(reportResponseData);
    }

    @GetMapping("/reports/")
    public ResponseEntity<List<ReportResponseData>> get(@Valid @RequestBody ReportInstance reportInstance){
        List<Report> reportList = reportService.get(reportInstance.getUserId(), reportInstance.getStartDate(), reportInstance.getEndDate());

        if(reportList.size() == 0){
            return new ResponseEntity("Не найдены отчеты по userId = "+reportInstance.getUserId(), HttpStatus.NOT_FOUND);
        }

        List<ReportResponseData> reportResponseDataList = new ArrayList<>();
        ReportResponseData reportResponseData = null;
        ReportResponse reportResponse = null;
        for(Report report: reportList ) {
            reportResponseData = new ReportResponseData();
            reportResponse = new ReportResponse();
            reportResponse.setReportId(report.getId().toString());
            reportResponse.setUserId(report.getUserId());
            reportResponse.setStartDate(report.getStartDate());
            reportResponse.setEndDate(report.getEndDate());
            reportResponse.setSaldoIn(report.getSaldoIn());
            reportResponse.setSaldoOut(report.getSaldoOut());
            reportResponseData.setData(reportResponse);
            reportResponseDataList.add(reportResponseData);
        }
        return ResponseEntity.ok(reportResponseDataList);
    }

    @GetMapping("/reports/{report_id}/export/")
    public ResponseEntity<byte[]> getExport(@PathVariable("report_id") Long reportId){
        Report report = reportService.get(reportId);

        if(report == null){
            return new ResponseEntity("Не найден отчет по Id", HttpStatus.NOT_FOUND);
        }

        StringBuilder csvData = new StringBuilder();
        csvData.append("ReportId;UserId;StartDate;EndDate;SaldoIn;SaldoOut\n");
        csvData.append(reportId.toString()+";"+
                        report.getUserId().toString()+";"+
                        report.getStartDate().toString()+";"+
                        report.getEndDate().toString()+";"+
                        report.getSaldoIn().toString()+";"+
                        report.getSaldoOut().toString()+"\n");

        byte[] csvBytes = csvData.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_"+reportId+".csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @DeleteMapping("/reports/{report_id}/")
    public ResponseEntity<String> delete(@PathVariable("report_id") Long reportId){
        Report report = reportService.get(reportId);

        if(report == null){
            return new ResponseEntity("Не найден отчет по Id", HttpStatus.NOT_FOUND);
        }
        reportService.delete(reportId);
        return new ResponseEntity("Удален отчет Id = "+reportId, HttpStatus.OK);
    }


}
