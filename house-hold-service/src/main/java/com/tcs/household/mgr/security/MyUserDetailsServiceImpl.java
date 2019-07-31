package com.tcs.household.mgr.security;

import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemUserInfoDao;
import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.mgr.security.model.MyUserFactory;
import com.tcs.household.persistent.entity.SystemUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取用户信息
 * Created by chongshan.tian01.
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService{


    @Autowired
    private SystemUserInfoDao userDao;

    @Autowired
    private SysCommonDao cmnDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SystemUserInfo user = userDao.getUserByLoginName(userName);
        if (user == null) {
            return null;
        }

        List<SystemPermissionInfo> lisPermission = cmnDao.getPermissonByLoginName(userName);
        List<String> lisRole = cmnDao.getUserRoleName( userName);
        for (String roleName : lisRole) {
            SystemPermissionInfo p = new SystemPermissionInfo();
            p.setPermission(roleName);
            lisPermission.add(p);
        }
        return MyUserFactory.createUserAuthInfo(user, lisPermission);
    }
    /**
     * 保存用户登录信息
     * @param loginName
     * @param requestIp
     */
    public void saveUserLoginInfo(String loginName, String requestIp) {
        userDao.saveUserLoginInfo(loginName, requestIp);
    }
}
