package com.erp.framework.security;

import com.erp.common.constant.Constants;
import com.erp.common.result.ResultCode;
import com.erp.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // Injected via setter after erp-system module is available
    private Object userService;

    public void setUserService(Object userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // This will be called via the full implementation in erp-system
        throw new BaseException(ResultCode.USER_NOT_FOUND);
    }

    public UserDetails loadUserByUserId(Long userId) {
        // This will be called via the full implementation in erp-system
        throw new BaseException(ResultCode.USER_NOT_FOUND);
    }

    public static LoginUser buildLoginUser(Long userId, String username, String password,
                                            Integer status, Set<String> permissions, List<String> roles) {
        return new LoginUser(userId, username, password, status, permissions, roles);
    }
}
