package com.tcs.household.mgr.facade;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.Page;
import com.tcs.household.enums.LoginUserTypeEnums;
import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemUserInfoDao;
import com.tcs.household.mgr.dao.SystemUserRoleDao;
import com.tcs.household.mgr.model.request.UserListRequest;
import com.tcs.household.mgr.model.request.UserRequest;
import com.tcs.household.mgr.model.response.*;
import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.mgr.security.model.UserAuthInfo;
import com.tcs.household.persistent.entity.SystemUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by chongshan.tian01.
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserFacadeService {

    @Autowired
    private SystemUserInfoDao userInfoDao;

    @Autowired
    private SystemUserRoleDao userRoleDao;

    @Autowired
    private SysCommonDao sysCommonDao;

    /**
     * 新增用户
     * @param user
     */
    public void saveUser(UserRequest user) {
        SystemUserInfo userInfo=new SystemUserInfo();
        userInfo.setLoginName(user.getLoginName());
        userInfo.setUsername(user.getUsername());
        String encryptPwd = SecureUtil.md5(user.getPassword() + user.getMobileNo());
        userInfo.setPassword(encryptPwd);
        userInfo.setMobileNo(user.getMobileNo());
        userInfo.setIsFreeze(0);
        userInfo.setUpdateTime(new Date());
        userInfo.setCreateTime(new Date());
        userInfo.setType(user.getType());
        userInfo.setFlag(0);
        userInfo.setUserCode(UUID.randomUUID().toString());
        userInfoDao.saveSelective(userInfo);
        //userRoleDao.addUserRole(userInfo.getId(),user.getRoleId());
    }

    /**
     * 取得登录用户信息(基本信息，菜单，权限)
     * @param loginName
     * @return
     */
    public LoginUserInfo getLoginUserInfo(String loginName) {
        LoginUserInfo user = new LoginUserInfo();
        UserAuthInfo UserAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 取得用户信息
        SystemUserInfo sysUser = userInfoDao.getUserByLoginName(loginName);
        user.setLoginName(sysUser.getLoginName());
        user.setUserName(sysUser.getUsername());


        //取得菜单信息
        List<SystemPermissionInfo> lisMenu = sysCommonDao.getMenuByLoginName(loginName);
        user.setMenu(convertMenu(lisMenu));

        //取得 权限信息
        List<String> lisPerm = new ArrayList<>();
        List<SystemPermissionInfo> lisPermission = sysCommonDao.getPermissonByLoginName(loginName);
        for (SystemPermissionInfo p : lisPermission) {
            if (!StringUtils.isEmpty(p.getPermission()) && !lisPerm.contains(p.getPermission())) {
                lisPerm.add(p.getPermission());
            }
        }
        user.setPermissions(lisPerm);
        return user;
    }

    /**
     * 保存用户登录信息
     * @param loginName
     * @param requestIp
     */
    public void saveUserLoginInfo(String loginName, String requestIp) {
        userInfoDao.saveUserLoginInfo(loginName, requestIp);
    }

    /**
     * 分页获取用户数据
     * @param param
     * @return
     */
    public UserListResponse getUserList(UserListRequest param) {
        UserListResponse resp = new UserListResponse();
        UserAuthInfo UserAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<SystemUserInfo> lisUser = userInfoDao.getUserList(param);
        resp.copyPageInfo(lisUser);
        List<UserListItem> userItemList = new ArrayList<>();
        List<Integer> lisUserId = new ArrayList<>();
        for (SystemUserInfo u : lisUser.getResult()) {
            UserListItem item = new UserListItem();
            item.setCreateTime(u.getCreateTime());
            item.setLastLoginIp(u.getLastLoginIp());
            item.setLastLoginTime(u.getLastLoginTime());
            item.setLoginName(u.getLoginName());
            item.setMobileNo(u.getMobileNo());
            item.setUserName(u.getUsername());
            item.setId(u.getId());
            item.setFreeze(u.getIsFreeze());
            userItemList.add(item);
        }
        Map<Integer, String> map = sysCommonDao.getUserRoles(lisUserId);
        for (UserListItem item : userItemList) {
            item.setRoles(map.get(item.getId()));
        }
        resp.setList(userItemList);
        return resp;
    }

    /**
     * 取得用户信息
     * @param userId
     */
    public UserInfo getUserInfo(Integer userId) {
        SystemUserInfo u = userInfoDao.queryById(userId);
        if (u == null || 1 == u.getFlag()) {
            throw new BizException(MessageCode.User_not_Exists.getCode(),
                    MessageCode.User_not_Exists.getMessage());
        }
        UserInfo user = new UserInfo();
        user.setUserId(u.getId());
        user.setLoginName(u.getLoginName());
        user.setMobileNo(u.getMobileNo());
        user.setUserName(u.getUsername());
        user.setRoles(userRoleDao.getUserRoles(userId));
        return user;
    }
    /**
     * 更新用户
     * @param user
     */
    @Transactional
    public void updateUser(UserRequest user) {
        if (userInfoDao.userExistsForUpdate(user.getUserId(), user.getLoginName())) {
            throw new BizException(MessageCode.User_Exists, user.getLoginName());
        }
        UserAuthInfo UserAuthInfo = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userInfoDao.updateUser(user);
        userRoleDao.removeDataByUserId(user.getUserId());
        userRoleDao.addUserRole(user.getUserId(), user.getRoleId());
    }
    /**
     * 删除用户
     * @param user
     */
    @Transactional
    public void removeUser(UserRequest user) {
        userInfoDao.removeUser(user.getUserId());
        userRoleDao.removeDataByUserId(user.getUserId());
    }

    /**
     * 冻结用户
     * @param user
     */
    public void freezeUser(UserRequest user, Integer freezeFlg) {
        userInfoDao.freezeUser(user.getUserId(), freezeFlg);
    }



    /**
     *
     * @param loginName
     */
    private void checkUserLoginName(String loginName) {
        SystemUserInfo u = new SystemUserInfo();
        u.setLoginName(loginName);
        if (userInfoDao.queryCount(u) > 0) {
            throw new BizException(MessageCode.User_Exists, loginName);
        }
    }


    private List<MenuInfo> convertMenu(List<SystemPermissionInfo> lisMenu) {
        List<MenuInfo> retList = new ArrayList<>();
        Map<Integer, MenuInfo> mapMenuInfo = new HashMap<>();
        for (SystemPermissionInfo p : lisMenu) {
            if (p.getPid() == 0) {
                MenuInfo m = new MenuInfo();
                m.setId(p.getId());
                m.setMenuName(p.getPermName());
                m.setPermission(p.getPermission());
                m.setUrl(p.getUrl());
                m.setIcon(p.getIcon());
                m.setPId(p.getId());
                m.setChildren(new ArrayList<MenuInfo>());
                retList.add(m);
                mapMenuInfo.put(p.getId(), m);
            } else {
                MenuInfo m = new MenuInfo();
                m.setId(p.getId());
                m.setMenuName(p.getPermName());
                m.setPermission(p.getPermission());
                m.setUrl(p.getUrl());
                m.setIcon(p.getIcon());
                m.setPId(p.getId());
                m.setChildren(new ArrayList<MenuInfo>());
                mapMenuInfo.get(p.getPid()).getChildren().add(m);
            }
        }
        for (int i = retList.size() - 1; i >= 0; i--) {
            MenuInfo m = retList.get(i);
            if (m.getChildren() == null || m.getChildren().size() == 0) {
                retList.remove(i);
            }
        }
        return retList;
    }
}
