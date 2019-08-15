package com.tcs.household.mgr.facade;

import cn.hutool.crypto.SecureUtil;
import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemUserInfoDao;
import com.tcs.household.mgr.dao.SystemUserRoleDao;
import com.tcs.household.mgr.model.request.UserRequest;
import com.tcs.household.mgr.model.response.LoginUserInfo;
import com.tcs.household.mgr.model.response.MenuInfo;
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
