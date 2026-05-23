package com.erp.system.dto;

import lombok.Data;

@Data
public class OperLogQuery {
    private String title;
    private String operName;
    private Integer status;
    private Long page = 1L;
    private Long pageSize = 10L;
}
