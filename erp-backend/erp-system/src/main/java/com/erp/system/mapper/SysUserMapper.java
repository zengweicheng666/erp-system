package com.erp.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erp.system.entity.SysUser;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT p.perms FROM sys_user_role ur " +
            "LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id " +
            "LEFT JOIN sys_menu p ON rm.menu_id = p.menu_id " +
            "WHERE ur.user_id = #{userId} AND p.perms IS NOT NULL AND p.perms != ''")
    Set<String> selectUserPermissions(Long userId);
}
