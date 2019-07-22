package com.tcs.household.mgr.security;

import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemUserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
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
