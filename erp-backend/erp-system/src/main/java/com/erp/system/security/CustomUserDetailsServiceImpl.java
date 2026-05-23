package com.erp.system.security;

import com.erp.framework.security.LoginUser;
import com.erp.framework.security.UserDetailsServiceImpl;
import com.erp.system.entity.SysRole;
import com.erp.system.mapper.SysRoleMapper;
import com.erp.system.mapper.SysUserMapper;
import com.erp.system.entity.SysUser;
import com.erp.system.mapper.SysMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl extends UserDetailsServiceImpl {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
        );
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return buildLoginUser(user);
    }

    public UserDetails loadUserByUserId(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + userId);
        }
        return buildLoginUser(user);
    }

    private LoginUser buildLoginUser(SysUser user) {
        Set<String> permissions = sysUserMapper.selectUserPermissions(user.getUserId());
        List<SysRole> roles = sysRoleMapper.selectRolesByUserId(user.getUserId());
        return new LoginUser(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getStatus(),
                permissions,
                roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList())
        );
    }
}
