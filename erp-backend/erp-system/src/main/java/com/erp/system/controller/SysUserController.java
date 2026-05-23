package com.erp.system.controller;

import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.system.dto.UserQuery;
import com.erp.system.entity.SysUser;
import com.erp.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @Operation(summary = "分页查询用户列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:user:list')")
    public Result<PageResult<SysUser>> list(UserQuery query) {
        return Result.success(sysUserService.listUsers(query));
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result<SysUser> get(@PathVariable Long userId) {
        return Result.success(sysUserService.getById(userId));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("hasAuthority('system:user:add')")
    public Result<Void> add(@RequestBody SysUser user) {
        sysUserService.createUser(user);
        return Result.success();
    }

    @Operation(summary = "修改用户")
    @PutMapping
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<Void> edit(@RequestBody SysUser user) {
        sysUserService.updateUser(user);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('system:user:remove')")
    public Result<Void> remove(@PathVariable Long userId) {
        sysUserService.removeById(userId);
        return Result.success();
    }

    @Operation(summary = "重置密码")
    @PutMapping("/resetPwd/{userId}")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<Void> resetPwd(@PathVariable Long userId) {
        sysUserService.resetPassword(userId);
        return Result.success();
    }

    @Operation(summary = "修改用户状态")
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<Void> updateStatus(@RequestParam Long userId, @RequestParam Integer status) {
        sysUserService.updateStatus(userId, status);
        return Result.success();
    }
}
