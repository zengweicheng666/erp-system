package com.erp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.system.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> listMenus();
    List<SysMenu> getMenusByUserId(Long userId);
    List<SysMenu> buildTree(List<SysMenu> menus);
    boolean createMenu(SysMenu menu);
    boolean updateMenu(SysMenu menu);
    List<Long> getMenuIdsByRoleId(Long roleId);
}
