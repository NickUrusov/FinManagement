package edu.innotech;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.innotech.dto.ReportInstance;
import edu.innotech.dto.ReportResponseData;
import edu.innotech.model.Report;
import edu.innotech.repository.ReportRepository;
import edu.innotech.services.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TestReports {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private ReportService reportService;

    private ReportInstance reportInstance  = null;
    private Report report = null;

    @BeforeEach
    void initReq() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        report = new Report();
        report.setId(3L);
        report.setUserId(1L);
        report.setStartDate(formatter.parse("2024-04-01", new ParsePosition(0)));
        report.setEndDate(formatter.parse("2024-10-01", new ParsePosition(0)));
        report.setSaldoIn(new BigDecimal(1055.25));
        report.setSaldoOut(new BigDecimal(898.99));
        reportInstance = new ReportInstance();
        reportInstance.setReportId(report.getId());
        reportInstance.setUserId(report.getUserId());
        reportInstance.setStartDate(report.getStartDate());
        reportInstance.setEndDate(report.getEndDate());
        reportInstance.setSaldoIn(report.getSaldoIn());
        reportInstance.setSaldoOut(report.getSaldoOut());
    }

    @Test
    public void testReportPost()  throws Exception {
        Mockito.when(reportService.post(Mockito.any(), Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(report);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(reportInstance);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/reports/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ReportResponseData reportResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ReportResponseData.class);
        Assertions.assertEquals(report.getId().toString(), reportResponseData.getData().getReportId());
    }

    @Test
    public void testReportGetById()  throws Exception {
        Mockito.when(reportService.get(Mockito.any())).thenReturn(report);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/reports/"+report.getId().toString()+"/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ReportResponseData reportResponseData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ReportResponseData.class);
        Assertions.assertEquals(report.getId().toString(), reportResponseData.getData().getReportId());
    }

    @Test
    public void testReportGet()  throws Exception {
        List<Report> reportList = new ArrayList<>();
        reportList.add(report);

        Mockito.when(reportService.get(Mockito.any(), Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(reportList);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(reportInstance);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/reports/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<ReportResponseData> reportResponseDataList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        Assertions.assertEquals(1, reportResponseDataList.size());
    }

    @Test
    public void testReportGetExport()  throws Exception {
        Mockito.when(reportService.get(Mockito.any())).thenReturn(report);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/reports/"+report.getId().toString()+"/export/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        StringBuilder csvData = new StringBuilder();
        csvData.append("ReportId;UserId;StartDate;EndDate;SaldoIn;SaldoOut\n");
        csvData.append(report.getId().toString()+";"+
                report.getUserId().toString()+";"+
                report.getStartDate().toString()+";"+
                report.getEndDate().toString()+";"+
                report.getSaldoIn().toString()+";"+
                report.getSaldoOut().toString()+"\n");

        Assertions.assertEquals(csvData.toString(), mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testDeleteReport() throws Exception {
        String testString = "Удален отчет Id = "+report.getId().toString();
        Mockito.when(reportService.get(Mockito.any())).thenReturn(report);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/reports/"+report.getId().toString()+"/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertEquals(testString, mvcResult.getResponse().getContentAsString());
    }

}
