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

        SysMenu purMgr = insertMenu(null, "采购管理", 0L, 3, "/purchase", "Layout", 0, "ShoppingCart");
        Long supM = insertMenu("供应商管理", purMgr.getMenuId(), 1, "supplier", "/purchase/supplier/index", 0, "User");
        insertMenu(null, "供应商查询", supM, 1, null, null, 1, null);
        insertMenu(null, "供应商新增", supM, 2, null, null, 1, null);
        insertMenu(null, "供应商修改", supM, 3, null, null, 1, null);
        insertMenu(null, "供应商删除", supM, 4, null, null, 1, null);

        Long poM = insertMenu("采购订单", purMgr.getMenuId(), 2, "order", "/purchase/order/index", 0, "List");
        insertMenu(null, "采购订单查询", poM, 1, null, null, 1, null);
        insertMenu(null, "采购订单新增", poM, 2, null, null, 1, null);
        insertMenu(null, "采购订单修改", poM, 3, null, null, 1, null);
        insertMenu(null, "采购订单删除", poM, 4, null, null, 1, null);

        Long inboundM = insertMenu("采购入库", purMgr.getMenuId(), 3, "inbound", "/purchase/inbound/index", 0, "Upload");
        insertMenu(null, "入库查询", inboundM, 1, null, null, 1, null);
        insertMenu(null, "入库操作", inboundM, 2, null, null, 1, null);

        Long purRetM = insertMenu("采购退货", purMgr.getMenuId(), 4, "preturn", "/purchase/return/index", 0, "RefreshLeft");
        insertMenu(null, "退货查询", purRetM, 1, null, null, 1, null);
        insertMenu(null, "退货新增", purRetM, 2, null, null, 1, null);

        SysMenu salMgr = insertMenu(null, "销售管理", 0L, 4, "/sales", "Layout", 0, "ShoppingBag");
        Long cusM = insertMenu("客户管理", salMgr.getMenuId(), 1, "customer", "/sales/customer/index", 0, "User");
        insertMenu(null, "客户查询", cusM, 1, null, null, 1, null);
        insertMenu(null, "客户新增", cusM, 2, null, null, 1, null);
        insertMenu(null, "客户修改", cusM, 3, null, null, 1, null);
        insertMenu(null, "客户删除", cusM, 4, null, null, 1, null);

        Long soM = insertMenu("销售订单", salMgr.getMenuId(), 2, "order", "/sales/order/index", 0, "List");
        insertMenu(null, "销售订单查询", soM, 1, null, null, 1, null);
        insertMenu(null, "销售订单新增", soM, 2, null, null, 1, null);
        insertMenu(null, "销售订单修改", soM, 3, null, null, 1, null);
        insertMenu(null, "销售订单删除", soM, 4, null, null, 1, null);

        Long outboundM = insertMenu("销售出库", salMgr.getMenuId(), 3, "outbound", "/sales/outbound/index", 0, "Download");
        insertMenu(null, "出库查询", outboundM, 1, null, null, 1, null);
        insertMenu(null, "出库操作", outboundM, 2, null, null, 1, null);

        Long salRetM = insertMenu("销售退货", salMgr.getMenuId(), 4, "sreturn", "/sales/return/index", 0, "RefreshRight");
        insertMenu(null, "退货查询", salRetM, 1, null, null, 1, null);
        insertMenu(null, "退货新增", salRetM, 2, null, null, 1, null);

        SysMenu finMgr = insertMenu(null, "财务管理", 0L, 5, "/finance", "Layout", 0, "Coin");
        Long recvM = insertMenu("应收账款", finMgr.getMenuId(), 1, "receivable", "/finance/receivable/index", 0, "Wallet");
        insertMenu(null, "应收查询", recvM, 1, null, null, 1, null);
        insertMenu(null, "应收新增", recvM, 2, null, null, 1, null);
        insertMenu(null, "收款操作", recvM, 3, null, null, 1, null);

        Long payM = insertMenu("应付账款", finMgr.getMenuId(), 2, "payable", "/finance/payable/index", 0, "WalletFilled");
        insertMenu(null, "应付查询", payM, 1, null, null, 1, null);
        insertMenu(null, "应付新增", payM, 2, null, null, 1, null);
        insertMenu(null, "付款操作", payM, 3, null, null, 1, null);

        insertMenu("收款单", finMgr.getMenuId(), 3, "receipt", "/finance/receipt/index", 0, "Money");
        insertMenu("付款单", finMgr.getMenuId(), 4, "payment", "/finance/payment/index", 0, "Money");
        Long expM = insertMenu("费用管理", finMgr.getMenuId(), 5, "expense", "/finance/expense/index", 0, "Document");
        insertMenu(null, "费用查询", expM, 1, null, null, 1, null);
        insertMenu(null, "费用新增", expM, 2, null, null, 1, null);
        insertMenu(null, "费用删除", expM, 3, null, null, 1, null);

        SysMenu proMgr = insertMenu(null, "生产管理", 0L, 6, "/production", "Layout", 0, "SetUp");
        Long bomM = insertMenu("BOM管理", proMgr.getMenuId(), 1, "bom", "/production/bom/index", 0, "Link");
        insertMenu(null, "BOM新增", bomM, 1, null, null, 1, null);
        insertMenu(null, "BOM删除", bomM, 2, null, null, 1, null);
        Long prodOrderM = insertMenu("生产工单", proMgr.getMenuId(), 2, "order", "/production/order/index", 0, "List");
        insertMenu(null, "工单查询", prodOrderM, 1, null, null, 1, null);
        insertMenu(null, "工单新增", prodOrderM, 2, null, null, 1, null);
        insertMenu(null, "工单修改", prodOrderM, 3, null, null, 1, null);
        insertMenu(null, "工单删除", prodOrderM, 4, null, null, 1, null);
        insertMenu("工艺路线", proMgr.getMenuId(), 3, "process", "/production/process/index", 0, "Opportunity");
        insertMenu("工序报工", proMgr.getMenuId(), 4, "report", "/production/report/index", 0, "Finished");

        SysMenu hrMgr = insertMenu(null, "人力资源管理", 0L, 7, "/hr", "Layout", 0, "User");
        Long deptM = insertMenu("部门管理", hrMgr.getMenuId(), 1, "dept", "/hr/dept/index", 0, "FolderOpened");
        insertMenu(null, "部门查询", deptM, 1, null, null, 1, null);
        insertMenu(null, "部门新增", deptM, 2, null, null, 1, null);
        insertMenu(null, "部门修改", deptM, 3, null, null, 1, null);
        insertMenu(null, "部门删除", deptM, 4, null, null, 1, null);
        Long empM = insertMenu("员工管理", hrMgr.getMenuId(), 2, "employee", "/hr/employee/index", 0, "UserFilled");
        insertMenu(null, "员工查询", empM, 1, null, null, 1, null);
        insertMenu(null, "员工新增", empM, 2, null, null, 1, null);
        insertMenu(null, "员工修改", empM, 3, null, null, 1, null);
        insertMenu(null, "员工删除", empM, 4, null, null, 1, null);
        insertMenu("考勤管理", hrMgr.getMenuId(), 3, "attendance", "/hr/attendance/index", 0, "Calendar");
        insertMenu("薪资管理", hrMgr.getMenuId(), 4, "payroll", "/hr/payroll/index", 0, "Money");

        SysMenu crmMgr = insertMenu(null, "客户关系管理", 0L, 8, "/crm", "Layout", 0, "Connection");
        Long leadM = insertMenu("销售线索", crmMgr.getMenuId(), 1, "lead", "/crm/lead/index", 0, "Phone");
        insertMenu(null, "线索查询", leadM, 1, null, null, 1, null);
        insertMenu(null, "线索新增", leadM, 2, null, null, 1, null);
        insertMenu(null, "线索修改", leadM, 3, null, null, 1, null);
        insertMenu(null, "线索删除", leadM, 4, null, null, 1, null);
        Long oppM = insertMenu("商机管理", crmMgr.getMenuId(), 2, "opportunity", "/crm/opportunity/index", 0, "Opportunity");
        insertMenu(null, "商机查询", oppM, 1, null, null, 1, null);
        insertMenu(null, "商机新增", oppM, 2, null, null, 1, null);
        insertMenu(null, "商机修改", oppM, 3, null, null, 1, null);
        insertMenu(null, "商机删除", oppM, 4, null, null, 1, null);
        insertMenu("联系人", crmMgr.getMenuId(), 3, "contact", "/crm/contact/index", 0, "User");
        insertMenu("跟进记录", crmMgr.getMenuId(), 4, "followup", "/crm/followup/index", 0, "ChatDotRound");

        return new Long[]{
                sysMgr.getMenuId(), userM, roleM, menuM, dictM, logM,
                invMgr.getMenuId(), prodM, catM, whM, stockM,
                purMgr.getMenuId(), supM, poM, inboundM, purRetM,
                salMgr.getMenuId(), cusM, soM, outboundM, salRetM,
                finMgr.getMenuId(), recvM, payM, expM,
                proMgr.getMenuId(), bomM, prodOrderM,
                hrMgr.getMenuId(), deptM, empM,
                crmMgr.getMenuId(), leadM, oppM
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
                    case "供应商查询" -> "purchase:supplier:list";
                    case "供应商新增" -> "purchase:supplier:add";
                    case "供应商修改" -> "purchase:supplier:edit";
                    case "供应商删除" -> "purchase:supplier:remove";
                    case "采购订单查询" -> "purchase:order:list";
                    case "采购订单新增" -> "purchase:order:add";
                    case "采购订单修改" -> "purchase:order:edit";
                    case "采购订单删除" -> "purchase:order:remove";
                    case "入库查询" -> "purchase:inbound:list";
                    case "入库操作" -> "purchase:inbound:add";
                    case "退货查询" -> "purchase:return:list";
                    case "退货新增" -> "purchase:return:add";
                    case "客户查询" -> "sales:customer:list";
                    case "客户新增" -> "sales:customer:add";
                    case "客户修改" -> "sales:customer:edit";
                    case "客户删除" -> "sales:customer:remove";
                    case "销售订单查询" -> "sales:order:list";
                    case "销售订单新增" -> "sales:order:add";
                    case "销售订单修改" -> "sales:order:edit";
                    case "销售订单删除" -> "sales:order:remove";
                    case "出库查询" -> "sales:outbound:list";
                    case "出库操作" -> "sales:outbound:add";
                    case "退货查询" -> "sales:return:list";
                    case "退货新增" -> "sales:return:add";
                    case "应收查询" -> "finance:receivable:list";
                    case "应收新增" -> "finance:receivable:add";
                    case "收款操作" -> "finance:receivable:edit";
                    case "应付查询" -> "finance:payable:list";
                    case "应付新增" -> "finance:payable:add";
                    case "付款操作" -> "finance:payable:edit";
                    case "费用查询" -> "finance:expense:list";
                    case "费用新增" -> "finance:expense:add";
                    case "费用删除" -> "finance:expense:remove";
                    case "BOM新增" -> "production:bom:add";
                    case "BOM删除" -> "production:bom:remove";
                    case "工单查询" -> "production:order:list";
                    case "工单新增" -> "production:order:add";
                    case "工单修改" -> "production:order:edit";
                    case "工单删除" -> "production:order:remove";
                    case "部门查询" -> "hr:dept:list";
                    case "部门新增" -> "hr:dept:add";
                    case "部门修改" -> "hr:dept:edit";
                    case "部门删除" -> "hr:dept:remove";
                    case "员工查询" -> "hr:employee:list";
                    case "员工新增" -> "hr:employee:add";
                    case "员工修改" -> "hr:employee:edit";
                    case "员工删除" -> "hr:employee:remove";
                    case "线索查询" -> "crm:lead:list";
                    case "线索新增" -> "crm:lead:add";
                    case "线索修改" -> "crm:lead:edit";
                    case "线索删除" -> "crm:lead:remove";
                    case "商机查询" -> "crm:opportunity:list";
                    case "商机新增" -> "crm:opportunity:add";
                    case "商机修改" -> "crm:opportunity:edit";
                    case "商机删除" -> "crm:opportunity:remove";
                    default -> null;
                };
            }
            menu.setPerms(perms);
        }
        sysMenuMapper.insert(menu);
        return menu;
    }
}
