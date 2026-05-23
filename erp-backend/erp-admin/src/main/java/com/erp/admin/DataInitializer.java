package com.erp.admin;

import com.erp.system.entity.*;
import com.erp.system.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (sysUserMapper.selectCount(null) > 0) {
            log.info("Data already initialized, skipping.");
            return;
        }

        log.info("Initializing default data...");

        SysUser admin = new SysUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setRealName("系统管理员");
        admin.setStatus(0);
        sysUserMapper.insert(admin);

        SysRole adminRole = new SysRole();
        adminRole.setRoleName("超级管理员");
        adminRole.setRoleKey("admin");
        adminRole.setRoleSort(1);
        adminRole.setStatus(0);
        sysRoleMapper.insert(adminRole);

        SysRole userRole = new SysRole();
        userRole.setRoleName("普通用户");
        userRole.setRoleKey("user");
        userRole.setRoleSort(2);
        userRole.setStatus(0);
        sysRoleMapper.insert(userRole);

        SysUserRole ur = new SysUserRole();
        ur.setUserId(admin.getUserId());
        ur.setRoleId(adminRole.getRoleId());
        sysUserRoleMapper.insert(ur);

        Long[] menuIds = createMenus();
        for (Long menuId : menuIds) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(adminRole.getRoleId());
            rm.setMenuId(menuId);
            sysRoleMenuMapper.insert(rm);
        }

        log.info("Default data initialized successfully.");
    }

    private Long[] createMenus() {
        SysMenu sysMgr = insertMenu(null, "系统管理", 0L, 1, "/system", "Layout", 0, "Setting");
        Long userM = insertMenu("用户管理", sysMgr.getMenuId(), 1, "user", "/system/user/index", 0, "User");
        insertMenu(null, "用户查询", userM, 1, null, null, 1, null);
        insertMenu(null, "用户新增", userM, 2, null, null, 1, null);
        insertMenu(null, "用户修改", userM, 3, null, null, 1, null);
        insertMenu(null, "用户删除", userM, 4, null, null, 1, null);

        Long roleM = insertMenu("角色管理", sysMgr.getMenuId(), 2, "role", "/system/role/index", 0, "UserFilled");
        insertMenu(null, "角色查询", roleM, 1, null, null, 1, null);
        insertMenu(null, "角色新增", roleM, 2, null, null, 1, null);
        insertMenu(null, "角色修改", roleM, 3, null, null, 1, null);
        insertMenu(null, "角色删除", roleM, 4, null, null, 1, null);

        Long menuM = insertMenu("菜单管理", sysMgr.getMenuId(), 3, "menu", "/system/menu/index", 0, "Menu");
        insertMenu(null, "菜单查询", menuM, 1, null, null, 1, null);
        insertMenu(null, "菜单新增", menuM, 2, null, null, 1, null);
        insertMenu(null, "菜单修改", menuM, 3, null, null, 1, null);
        insertMenu(null, "菜单删除", menuM, 4, null, null, 1, null);

        Long dictM = insertMenu("字典管理", sysMgr.getMenuId(), 4, "dict", "/system/dict/index", 0, "Reading");
        insertMenu(null, "字典查询", dictM, 1, null, null, 1, null);
        insertMenu(null, "字典新增", dictM, 2, null, null, 1, null);
        insertMenu(null, "字典修改", dictM, 3, null, null, 1, null);
        insertMenu(null, "字典删除", dictM, 4, null, null, 1, null);

        Long logM = insertMenu("操作日志", sysMgr.getMenuId(), 5, "log", "/system/log/index", 0, "Document");
        insertMenu(null, "日志查询", logM, 1, null, null, 1, null);
        insertMenu(null, "日志删除", logM, 2, null, null, 1, null);

        SysMenu invMgr = insertMenu(null, "库存管理", 0L, 2, "/inventory", "Layout", 0, "Goods");
        Long prodM = insertMenu("商品管理", invMgr.getMenuId(), 1, "product", "/inventory/product/index", 0, "Goods");
        insertMenu(null, "商品查询", prodM, 1, null, null, 1, null);
        insertMenu(null, "商品新增", prodM, 2, null, null, 1, null);
        insertMenu(null, "商品修改", prodM, 3, null, null, 1, null);
        insertMenu(null, "商品删除", prodM, 4, null, null, 1, null);

        Long catM = insertMenu("商品分类", invMgr.getMenuId(), 2, "category", "/inventory/category/index", 0, "FolderOpened");
        insertMenu(null, "分类查询", catM, 1, null, null, 1, null);
        insertMenu(null, "分类新增", catM, 2, null, null, 1, null);
        insertMenu(null, "分类修改", catM, 3, null, null, 1, null);
        insertMenu(null, "分类删除", catM, 4, null, null, 1, null);

        Long whM = insertMenu("仓库管理", invMgr.getMenuId(), 3, "warehouse", "/inventory/warehouse/index", 0, "HomeFilled");
        insertMenu(null, "仓库查询", whM, 1, null, null, 1, null);
        insertMenu(null, "仓库新增", whM, 2, null, null, 1, null);
        insertMenu(null, "仓库修改", whM, 3, null, null, 1, null);
        insertMenu(null, "仓库删除", whM, 4, null, null, 1, null);

        Long stockM = insertMenu("库存管理", invMgr.getMenuId(), 4, "stock", "/inventory/stock/index", 0, "Coin");
        insertMenu(null, "库存查询", stockM, 1, null, null, 1, null);
        insertMenu(null, "入库操作", stockM, 2, null, null, 1, null);
        insertMenu(null, "出库操作", stockM, 3, null, null, 1, null);

        insertMenu("库存流水", invMgr.getMenuId(), 5, "records", "/inventory/stock/records", 0, "List");

        return new Long[]{
                sysMgr.getMenuId(), userM, roleM, menuM, dictM, logM,
                invMgr.getMenuId(), prodM, catM, whM, stockM
        };
    }

    private SysMenu insertMenu(String name, Long parentId, int order, String path, String component, int menuType, String icon) {
        SysMenu menu = new SysMenu();
        menu.setMenuName(name);
        menu.setParentId(parentId);
        menu.setOrderNum(order);
        menu.setPath(path);
        menu.setComponent(component);
        menu.setMenuType(menuType);
        menu.setVisible("0");
        menu.setStatus("0");
        menu.setIcon(icon);
        if (menuType == 1) {
            String perms = null;
            if (name != null) {
                perms = switch (name) {
                    case "用户查询" -> "system:user:list";
                    case "用户新增" -> "system:user:add";
                    case "用户修改" -> "system:user:edit";
                    case "用户删除" -> "system:user:remove";
                    case "角色查询" -> "system:role:list";
                    case "角色新增" -> "system:role:add";
                    case "角色修改" -> "system:role:edit";
                    case "角色删除" -> "system:role:remove";
                    case "菜单查询" -> "system:menu:list";
                    case "菜单新增" -> "system:menu:add";
                    case "菜单修改" -> "system:menu:edit";
                    case "菜单删除" -> "system:menu:remove";
                    case "字典查询" -> "system:dict:list";
                    case "字典新增" -> "system:dict:add";
                    case "字典修改" -> "system:dict:edit";
                    case "字典删除" -> "system:dict:remove";
                    case "日志查询" -> "system:log:list";
                    case "日志删除" -> "system:log:remove";
                    case "商品查询" -> "inventory:product:list";
                    case "商品新增" -> "inventory:product:add";
                    case "商品修改" -> "inventory:product:edit";
                    case "商品删除" -> "inventory:product:remove";
                    case "分类查询" -> "inventory:category:list";
                    case "分类新增" -> "inventory:category:add";
                    case "分类修改" -> "inventory:category:edit";
                    case "分类删除" -> "inventory:category:remove";
                    case "仓库查询" -> "inventory:warehouse:list";
                    case "仓库新增" -> "inventory:warehouse:add";
                    case "仓库修改" -> "inventory:warehouse:edit";
                    case "仓库删除" -> "inventory:warehouse:remove";
                    case "库存查询" -> "inventory:stock:list";
                    case "入库操作" -> "inventory:stock:in";
                    case "出库操作" -> "inventory:stock:out";
                    default -> null;
                };
            }
            menu.setPerms(perms);
        }
        sysMenuMapper.insert(menu);
        return menu;
    }
}
