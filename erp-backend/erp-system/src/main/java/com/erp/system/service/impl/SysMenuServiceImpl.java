package com.erp.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.system.entity.SysMenu;
import com.erp.system.entity.SysRoleMenu;
import com.erp.system.mapper.SysMenuMapper;
import com.erp.system.mapper.SysRoleMenuMapper;
import com.erp.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> listMenus() {
        return sysMenuMapper.selectAllMenus();
    }

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        return sysMenuMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<SysMenu> buildTree(List<SysMenu> menus) {
        List<SysMenu> tree = new ArrayList<>();
        List<SysMenu> flat = menus.stream()
                .filter(m -> m.getMenuId() != null)
                .collect(Collectors.toList());

        for (SysMenu menu : flat) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                tree.add(findChildren(menu, flat));
            }
        }
        return tree;
    }

    private SysMenu findChildren(SysMenu parent, List<SysMenu> flat) {
        List<SysMenu> children = new ArrayList<>();
        for (SysMenu menu : flat) {
            if (parent.getMenuId().equals(menu.getParentId())) {
                children.add(findChildren(menu, flat));
            }
        }
        parent.setChildren(children);
        return parent;
    }

    @Override
    public boolean createMenu(SysMenu menu) {
        return save(menu);
    }

    @Override
    public boolean updateMenu(SysMenu menu) {
        return updateById(menu);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        return sysRoleMenuMapper.selectList(wrapper).stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
    }
}
