package com.erp.system.dto;

import lombok.Data;

@Data
public class UserQuery {
    private String username;
    private String realName;
    private Integer status;
    private Long page = 1L;
    private Long pageSize = 10L;
}
