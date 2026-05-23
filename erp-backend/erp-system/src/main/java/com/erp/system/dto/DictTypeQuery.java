package com.erp.system.dto;

import lombok.Data;

@Data
public class DictTypeQuery {
    private String dictName;
    private String dictType;
    private Integer status;
    private Long page = 1L;
    private Long pageSize = 10L;
}
