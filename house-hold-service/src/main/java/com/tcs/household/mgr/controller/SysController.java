package com.tcs.household.mgr.controller;

import com.tcs.household.enums.RedisConstant;
import com.tcs.household.mgr.facade.LoginFacadeService;
import com.tcs.household.mgr.facade.MenuFacadeService;
import com.tcs.household.mgr.facade.RoleFacadeService;
import com.tcs.household.mgr.facade.SysUserFacadeService;
import com.tcs.household.mgr.model.request.LoginUser;
import com.tcs.household.mgr.model.request.UserRequest;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import com.tcs.household.model.response.JsonResponse;
import com.tcs.household.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chongshan.tian01.
 */
@RestController
public class SysController {

    @Autowired
    private SysUserFacadeService sysUserFacadeService;

    @Autowired
    private RoleFacadeService roleFacadeService;

    @Autowired
    private MenuFacadeService menuFacadeService;


    //登录Token

    @Value("${auth.header}")
    private String tokenHeader;

    private LoginFacadeService loginFacadeService;

    private SysUserFacadeService userFacadeService;


    /**
     * 用户登录
     * @param loginUser
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "${admin.login.url}")
    public JsonResponse<String> createAuthenticationToken(@RequestBody LoginUser loginUser, HttpServletRequest request)
            throws AuthenticationException {
           final String token=loginFacadeService.login(loginUser,request);
           //存入redis
           RedisUtils.set(RedisConstant.REDIS_USER_LOGIN_TOKEN.getKey()+"_"+loginUser.getUsername(),token,36000);
           return JsonResponse.success(token);
    }

    /**
     * 验证Token
     * @param token
     * @return
     */
    @RequestMapping(value="${token.validate.url}", method= RequestMethod.POST)
    public JsonResponse<UserAuthInfo> tokenValidate(@RequestParam("token") String token) {
        return JsonResponse.success(loginFacadeService.tokenValidate(token));
    }

    /**
     *
     * 新增用户
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:add')")
    @RequestMapping("/api/sys/user/add")
    public JsonResponse<Void> addUser(@RequestBody UserRequest user){
        sysUserFacadeService.saveUser(user);
        return JsonResponse.success();

    }




























}
