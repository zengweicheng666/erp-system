package com.erp.sales.dto;
import lombok.Data;
@Data
public class SalesOrderQuery {
    private String orderNo; private Long customerId; private Integer status;
    private Long page = 1L; private Long pageSize = 10L;
}
