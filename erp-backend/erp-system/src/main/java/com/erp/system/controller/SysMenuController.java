package com.erp.system.controller;

import com.erp.common.result.Result;
import com.erp.system.entity.SysMenu;
import com.erp.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @Operation(summary = "获取菜单树(全部)")
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:menu:list')")
    public Result<List<SysMenu>> tree() {
        List<SysMenu> menus = sysMenuService.listMenus();
        return Result.success(sysMenuService.buildTree(menus));
    }

    @Operation(summary = "获取菜单详情")
    @GetMapping("/{menuId}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public Result<SysMenu> get(@PathVariable Long menuId) {
        return Result.success(sysMenuService.getById(menuId));
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    @PreAuthorize("hasAuthority('system:menu:add')")
    public Result<Void> add(@RequestBody SysMenu menu) {
        sysMenuService.createMenu(menu);
        return Result.success();
    }

    @Operation(summary = "修改菜单")
    @PutMapping
    @PreAuthorize("hasAuthority('system:menu:edit')")
    public Result<Void> edit(@RequestBody SysMenu menu) {
        sysMenuService.updateMenu(menu);
        return Result.success();
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{menuId}")
    @PreAuthorize("hasAuthority('system:menu:remove')")
    public Result<Void> remove(@PathVariable Long menuId) {
        sysMenuService.removeById(menuId);
        return Result.success();
    }

    @Operation(summary = "获取用户路由菜单")
    @GetMapping("/routers")
    public Result<List<SysMenu>> getRouters() {
        org.springframework.security.core.Authentication auth =
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        com.erp.framework.security.LoginUser loginUser = (com.erp.framework.security.LoginUser) auth.getPrincipal();
        List<SysMenu> menus = sysMenuService.getMenusByUserId(loginUser.getUserId());
        return Result.success(sysMenuService.buildTree(menus));
    }
}
