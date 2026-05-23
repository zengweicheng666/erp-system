package com.erp.finance.dto;
import lombok.Data;
@Data
public class FinanceQuery {
    private String invoiceNo; private Long customerId; private Long supplierId; private Integer status;
    private String expenseType; private String startDate; private String endDate;
    private Long page = 1L; private Long pageSize = 10L;
}
