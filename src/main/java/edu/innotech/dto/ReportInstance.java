package edu.innotech.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Data
public class ReportInstance {

    private Long reportId;

    @NotNull
    @Valid
    private Long userId;

    @NotNull
    @Valid
    private Date startDate;

    @NotNull
    @Valid
    private Date endDate;

    private BigDecimal saldoIn;
    private BigDecimal saldoOut;

}
