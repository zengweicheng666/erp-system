package com.erp.system.controller;

import com.erp.common.result.Result;
import com.erp.common.result.ResultCode;
import com.erp.common.exception.BaseException;
import com.erp.framework.security.JwtUtil;
import com.erp.system.dto.LoginRequest;
import com.erp.system.dto.LoginResponse;
import com.erp.system.entity.SysRole;
import com.erp.system.entity.SysUser;
import com.erp.system.service.SysMenuService;
import com.erp.system.service.SysRoleService;
import com.erp.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        SysUser user = sysUserService.getUserByUsername(request.getUsername());
        if (user == null) {
            return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }
        if (user.getStatus() == 1) {
            return Result.failed(ResultCode.USER_DISABLED);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }

        String token = jwtUtil.generateToken(user.getUserId(), user.getUsername());
        Set<String> permissions = sysUserService.getUserPermissions(user.getUserId());
        List<SysRole> roles = sysRoleService.getRolesByUserId(user.getUserId());

        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                user.getUserId(), user.getUsername(), user.getRealName(),
                user.getAvatar(), permissions,
                roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList())
        );

        return Result.success(new LoginResponse(token, userInfo));
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/userinfo")
    public Result<LoginResponse.UserInfo> getUserInfo() {
        org.springframework.security.core.Authentication auth =
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        com.erp.framework.security.LoginUser loginUser = (com.erp.framework.security.LoginUser) auth.getPrincipal();

        Set<String> permissions = sysUserService.getUserPermissions(loginUser.getUserId());
        List<SysRole> roles = sysRoleService.getRolesByUserId(loginUser.getUserId());

        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                loginUser.getUserId(), loginUser.getUsername(), null,
                null, permissions,
                roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList())
        );
        return Result.success(userInfo);
    }
}
