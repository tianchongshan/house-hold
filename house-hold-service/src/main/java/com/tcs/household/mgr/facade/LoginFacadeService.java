package com.tcs.household.mgr.facade;

import com.tcs.household.enums.MessageCode;
import com.tcs.household.enums.RedisConstant;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.model.request.LoginUser;
import com.tcs.household.mgr.security.MyUserDetailsServiceImpl;
import com.tcs.household.mgr.security.model.LoginUserToken;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import com.tcs.household.util.CommonUtils;
import com.tcs.household.util.JwtTokenUtil;
import com.tcs.household.util.RedisService;
import com.tcs.household.util.RedisUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private RedisService redisService;
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


    public UserAuthInfo tokenValidate(String token) {
        if (token != null) {
            final String authToken = token;
            Claims userInfo = jwtTokenUtil.getUserinfoFromToken(authToken);
            String username = userInfo.get(JwtTokenUtil.CLAIM_KEY_USERNAME, String.class);
            Object sessionToken = redisService.get(RedisConstant.REDIS_USER_LOGIN_TOKEN.getKey() + username);
            if (StringUtils.isEmpty(sessionToken)
                    || !sessionToken.toString().equals(token)) {
                throw new BizException(MessageCode.TOKEN_INVALID.getCode(),
                        MessageCode.TOKEN_INVALID.getMessage());
            }
            if (username != null) {
                UserAuthInfo userDetails = (UserAuthInfo)userDetailsService.loadUserByUsername(username);
                if (!jwtTokenUtil.validateToken(authToken, userDetails)) {
                    throw new BizException(MessageCode.TOKEN_INVALID.getCode(),
                            MessageCode.TOKEN_INVALID.getMessage());
                }
                return userDetails;
            }
            throw new BizException(MessageCode.TOKEN_INVALID.getCode(),
                    MessageCode.TOKEN_INVALID.getMessage());
        } else {
            throw new BizException(MessageCode.TOKEN_INVALID.getCode(),
                    MessageCode.TOKEN_INVALID.getMessage());
        }
    }
}
