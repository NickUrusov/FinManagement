package edu.innotech.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReportResponse {
    private String reportId;
    private Long userId;
    private Date startDate;
    private Date endDate;
    private BigDecimal saldoIn;
    private BigDecimal saldoOut;

}
