package com.erp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.system.dto.UserQuery;
import com.erp.system.entity.SysUser;
import com.erp.common.result.PageResult;

import java.util.Set;

public interface SysUserService extends IService<SysUser> {
    PageResult<SysUser> listUsers(UserQuery query);
    SysUser getUserByUsername(String username);
    Set<String> getUserPermissions(Long userId);
    boolean createUser(SysUser user);
    boolean updateUser(SysUser user);
    boolean resetPassword(Long userId);
    boolean updateStatus(Long userId, Integer status);
}
