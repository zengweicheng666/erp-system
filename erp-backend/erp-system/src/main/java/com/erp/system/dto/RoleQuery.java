package com.erp.system.dto;

import lombok.Data;

@Data
public class RoleQuery {
    private String roleName;
    private String roleKey;
    private Integer status;
    private Long page = 1L;
    private Long pageSize = 10L;
}
