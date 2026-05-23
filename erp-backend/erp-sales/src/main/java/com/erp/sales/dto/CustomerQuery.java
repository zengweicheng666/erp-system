package com.erp.sales.dto;
import lombok.Data;
@Data
public class CustomerQuery {
    private String customerCode; private String customerName; private Integer status;
    private Long page = 1L; private Long pageSize = 10L;
}
