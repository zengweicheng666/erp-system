package com.erp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.system.dto.RoleQuery;
import com.erp.system.entity.SysRole;
import com.erp.common.result.PageResult;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    PageResult<SysRole> listRoles(RoleQuery query);
    List<SysRole> getRolesByUserId(Long userId);
    boolean createRole(SysRole role);
    boolean updateRole(SysRole role);
    boolean assignMenus(Long roleId, List<Long> menuIds);
}
