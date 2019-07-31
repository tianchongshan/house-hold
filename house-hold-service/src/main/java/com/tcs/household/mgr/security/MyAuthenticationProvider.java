package com.tcs.household.mgr.security;

import cn.hutool.crypto.SecureUtil;
import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.dao.SystemUserInfoDao;
import com.tcs.household.mgr.security.model.LoginUserToken;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import com.tcs.household.persistent.entity.SystemUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Created by chongshan.tian01.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsServiceImpl userDetailService;

    @Autowired
    private SystemUserInfoDao userInfoDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginUserToken token= (LoginUserToken) authentication;
        String username=token.getName();
        String password= (String) token.getCredentials();
        SystemUserInfo user=userInfoDao.getUserByLoginName(username);
        if (user == null) {
            throw new BizException(MessageCode.USER_NOX_EXISTS);
        } else if (user.getIsFreeze().intValue() == 1) {
            throw new BadCredentialsException("用户被冻结");
        }

        String encryptPwd = SecureUtil.md5(password+user.getMobileNo());
        if(!encryptPwd.equals(user.getPassword())){
            throw new BadCredentialsException("用户密码认证失败");
        }

        UserAuthInfo userInfo = (UserAuthInfo)userDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userInfo, null, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
