package com.tcs.household.mgr.controller;

import com.tcs.household.enums.RedisConstant;
import com.tcs.household.mgr.facade.LoginFacadeService;
import com.tcs.household.mgr.facade.MenuFacadeService;
import com.tcs.household.mgr.facade.RoleFacadeService;
import com.tcs.household.mgr.facade.SysUserFacadeService;
import com.tcs.household.mgr.model.request.*;
import com.tcs.household.mgr.model.response.*;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import com.tcs.household.model.response.JsonResponse;
import com.tcs.household.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chongshan.tian01.
 */
@CrossOrigin
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

    @Autowired
    private LoginFacadeService loginFacadeService;

    @Autowired
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
    @RequestMapping("/sys/user/add")
    public JsonResponse<Void> addUser(@RequestBody UserRequest user){
        sysUserFacadeService.saveUser(user);
        return JsonResponse.success();

    }


    /**
     * 注销
     * @return
     */
    @PostMapping("${admin.logout.url}")
    public JsonResponse<Void> logout() {
        loginFacadeService.logout();
        return JsonResponse.success();
    }

    @PostMapping("/api/user/info")
    public JsonResponse<LoginUserInfo> getUserInfo(){
        UserAuthInfo jwtUser = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginUserInfo userInfo = userFacadeService.getLoginUserInfo(jwtUser.getUsername());
        return JsonResponse.success(userInfo);
    }




    //------------------------------ 用户相关操作 Start ---------------------------------------
    /**
     * 取得用户列表
     * @param param
     * @return
     */
    @PreAuthorize("hasAnyAuthority({'sys:user:list'})")
    @RequestMapping("/api/sys/user/list")
    public JsonResponse<UserListResponse> getUserList(@RequestBody UserListRequest param) {
        return JsonResponse.success(sysUserFacadeService.getUserList(param));
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:remove')")
    @RequestMapping("/api/sys/user/remove")
    public JsonResponse<Void> removeUser(@RequestBody UserRequest user) {
        sysUserFacadeService.removeUser(user);
        return JsonResponse.success(null);
    }

    /**
     * 冻结用户
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:freeze')")
    @RequestMapping("/api/sys/user/freeze")
    public JsonResponse<Void> freezeuser(@RequestBody UserRequest user) {
        sysUserFacadeService.freezeUser(user, 1);
        return JsonResponse.success(null);
    }

    /**
     * 解冻冻结用户
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:freeze')")
    @RequestMapping("/api/sys/user/unfreeze")
    public JsonResponse<Void> unFreezeuser(@RequestBody UserRequest user) {
        sysUserFacadeService.freezeUser(user, 0);
        return JsonResponse.success(null);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:update')")
    @RequestMapping("/api/sys/user/update")
    public JsonResponse<Void> modifyUser(@RequestBody UserRequest user) {
        sysUserFacadeService.updateUser(user);
        return JsonResponse.success(null);
    }

    @PreAuthorize("hasAuthority('sys:user:list')")
    @RequestMapping("/api/sys/user/info")
    public JsonResponse<UserInfo> getUserInfo(@RequestParam("userId") Integer userId) {
        return JsonResponse.success(sysUserFacadeService.getUserInfo(userId));
    }



    // ------------------------------ 用户相关操作 End ---------------------------------------



    // ------------------------------ 角色相关操作 Start ---------------------------------------
    /**
     * 取得角色列表
     * @param req
     * @return
     */
    @PreAuthorize("hasAnyAuthority({'sys:role:list'})")
    @RequestMapping("/api/sys/role/list")
    public JsonResponse<RoleListResponse> getRoleList(@RequestBody RoleListRequest req) {
        return JsonResponse.success(roleFacadeService.getRoleList(req));
    }

    /**
     * 取得角色信息
     * @param roleId
     * @return
     */
    @PreAuthorize("hasAnyAuthority({'sys:role:list'})")
    @RequestMapping("/api/sys/role/info")
    public JsonResponse<com.tcs.household.mgr.model.response.RoleInfo> getRoleInfo(@RequestParam("roleId") Integer roleId) {
        return JsonResponse.success(roleFacadeService.getRoleInfo(roleId));
    }

    /**
     * 新增角色
     * @param req
     * @return
     */
    @PreAuthorize("hasAnyAuthority({'sys:role:add'})")
    @RequestMapping("/api/sys/role/add")
    public JsonResponse<Void> addRole(@RequestBody RoleRequest req) {
        roleFacadeService.addNewRole(req);
        return JsonResponse.success();
    }

    /**
     * 修改角色
     * @param req
     * @return
     */
    @PreAuthorize("hasAnyAuthority({'sys:role:update'})")
    @RequestMapping("/api/sys/role/update")
    public JsonResponse<Void> modifyRole(@RequestBody RoleRequest req) {
        roleFacadeService.modifyRole(req);
        return JsonResponse.success();
    }

    /**
     * 修改角色
     * @param req
     * @return
     */
    @PreAuthorize("hasAnyAuthority({'sys:role:remove'})")
    @RequestMapping("/api/sys/role/remove")
    public JsonResponse<Void> removeRole(@RequestBody RoleRequest req) {
        roleFacadeService.removeRole(req);
        return JsonResponse.success();
    }

    // ------------------------------ 角色相关操作 End ---------------------------------------


    // ------------------------------ 菜单相关操作 Start ---------------------------------------
    /**
     * 取得所有菜单(系统管理员操作)
     * @return
     */
    @PreAuthorize("hasAnyAuthority({'sys:menu:list', 'sys:role:add', 'sys:role:update'})")
    @RequestMapping("/api/sys/menu/list")
    public JsonResponse<List<MenuInfo>> getAllMenuList() {
        return JsonResponse.success(menuFacadeService.getAllPermission());
    }

    /**
     * 添加新菜单
     * @param menu
     * @return
     */
    @PreAuthorize("hasAuthority('sys_admin') && hasAuthority('sys:menu:add')")
    @RequestMapping("/api/sys/menu/add")
    public JsonResponse<Void> addNewMenu(@RequestBody MenuRequest menu) {
        menuFacadeService.addNewMenu(menu);
        return JsonResponse.success();
    }

    /**
     * 修改菜单信息
     * @param menu
     * @return
     */
    @PreAuthorize("hasAuthority('sys_admin') && hasAuthority('sys:menu:modify')")
    @RequestMapping("/api/sys/menu/modify")
    public JsonResponse<Void> modifyMenu(@RequestBody MenuRequest menu) {
        menuFacadeService.modifyMenu(menu);
        return JsonResponse.success();
    }

    /**
     * 获取菜单信息
     * @param menuId
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @RequestMapping("/api/sys/menu/info")
    public JsonResponse<MenuItemInfo> getMenuInfo(@RequestParam("menuId") Integer menuId) {
        return JsonResponse.success(menuFacadeService.getMenuById(menuId));
    }

    /**
     * 删除菜单信息
     * @param menu
     * @return
     */
    @PreAuthorize("hasAuthority('sys_admin') && hasAuthority('sys:menu:remove')")
    @RequestMapping("/api/sys/menu/remove")
    public JsonResponse<Void> removeMenu(@RequestBody MenuRequest menu) {
        menuFacadeService.removeMenu(menu);
        return JsonResponse.success();
    }



}
