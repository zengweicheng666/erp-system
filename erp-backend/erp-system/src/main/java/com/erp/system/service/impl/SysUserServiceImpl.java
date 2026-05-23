package com.erp.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.constant.Constants;
import com.erp.common.exception.BaseException;
import com.erp.common.result.PageResult;
import com.erp.common.result.ResultCode;
import com.erp.system.dto.UserQuery;
import com.erp.system.entity.SysUser;
import com.erp.system.mapper.SysUserMapper;
import com.erp.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResult<SysUser> listUsers(UserQuery query) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getUsername())) {
            wrapper.like(SysUser::getUsername, query.getUsername());
        }
        if (StringUtils.hasText(query.getRealName())) {
            wrapper.like(SysUser::getRealName, query.getRealName());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SysUser::getStatus, query.getStatus());
        }
        wrapper.orderByAsc(SysUser::getCreateTime);

        Page<SysUser> page = new Page<>(query.getPage(), query.getPageSize());
        Page<SysUser> result = page(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        return sysUserMapper.selectUserPermissions(userId);
    }

    @Override
    public boolean createUser(SysUser user) {
        if (getUserByUsername(user.getUsername()) != null) {
            throw new BaseException(ResultCode.DUPLICATE_DATA, "用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
        return save(user);
    }

    @Override
    public boolean updateUser(SysUser user) {
        user.setPassword(null);
        return updateById(user);
    }

    @Override
    public boolean resetPassword(Long userId) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BaseException(ResultCode.USER_NOT_FOUND);
        }
        user.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
        return updateById(user);
    }

    @Override
    public boolean updateStatus(Long userId, Integer status) {
        SysUser user = getById(userId);
        if (user == null) {
            throw new BaseException(ResultCode.USER_NOT_FOUND);
        }
        user.setStatus(status);
        return updateById(user);
    }
}
