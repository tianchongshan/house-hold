package com.tcs.household.mgr.facade;

import com.tcs.household.mgr.model.request.LoginUser;
import com.tcs.household.mgr.security.MyUserDetailsServiceImpl;
import com.tcs.household.mgr.security.model.LoginUserToken;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import com.tcs.household.util.CommonUtils;
import com.tcs.household.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chongshan.tian01.
 */
@Service
public class LoginFacadeService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SysUserFacadeService sysUserFacadeService;

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;
    /**
     * 认证用户
     */
    public String login(LoginUser loginUser, HttpServletRequest request) {
        LoginUserToken upToken=new LoginUserToken(loginUser.getUsername(),loginUser.getPassword());
        final Authentication authentication=authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserAuthInfo userAuthInfo= (UserAuthInfo) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userAuthInfo);
        userDetailsService.saveUserLoginInfo(loginUser.getUsername(), CommonUtils.getRemoteIp(request));
        return token;
    }



















}
