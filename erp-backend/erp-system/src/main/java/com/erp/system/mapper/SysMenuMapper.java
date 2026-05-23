package com.erp.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.erp.system.entity.SysMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("SELECT DISTINCT m.* FROM sys_menu m " +
            "LEFT JOIN sys_role_menu rm ON m.menu_id = rm.menu_id " +
            "LEFT JOIN sys_user_role ur ON rm.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND m.status = '0' " +
            "ORDER BY m.parent_id, m.order_num")
    List<SysMenu> selectMenusByUserId(Long userId);

    @Select("SELECT m.* FROM sys_menu m WHERE m.status = '0' ORDER BY m.parent_id, m.order_num")
    List<SysMenu> selectAllMenus();
}
