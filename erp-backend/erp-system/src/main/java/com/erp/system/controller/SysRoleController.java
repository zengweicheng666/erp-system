package com.erp.system.controller;

import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.system.dto.RoleQuery;
import com.erp.system.entity.SysRole;
import com.erp.system.service.SysMenuService;
import com.erp.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;

    @Operation(summary = "分页查询角色列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result<PageResult<SysRole>> list(RoleQuery query) {
        return Result.success(sysRoleService.listRoles(query));
    }

    @Operation(summary = "获取角色详情")
    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<SysRole> get(@PathVariable Long roleId) {
        return Result.success(sysRoleService.getById(roleId));
    }

    @Operation(summary = "新增角色")
    @PostMapping
    @PreAuthorize("hasAuthority('system:role:add')")
    public Result<Void> add(@RequestBody SysRole role) {
        sysRoleService.createRole(role);
        return Result.success();
    }

    @Operation(summary = "修改角色")
    @PutMapping
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> edit(@RequestBody SysRole role) {
        sysRoleService.updateRole(role);
        return Result.success();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public Result<Void> remove(@PathVariable Long roleId) {
        sysRoleService.removeById(roleId);
        return Result.success();
    }

    @Operation(summary = "获取角色分配的菜单ID")
    @GetMapping("/menuIds/{roleId}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<List<Long>> getMenuIds(@PathVariable Long roleId) {
        return Result.success(sysMenuService.getMenuIdsByRoleId(roleId));
    }

    @Operation(summary = "分配菜单权限")
    @PutMapping("/assignMenus")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> assignMenus(@RequestBody Map<String, Object> params) {
        Long roleId = Long.valueOf(params.get("roleId").toString());
        @SuppressWarnings("unchecked")
        List<Long> menuIds = (List<Long>) params.get("menuIds");
        sysRoleService.assignMenus(roleId, menuIds);
        return Result.success();
    }
}
