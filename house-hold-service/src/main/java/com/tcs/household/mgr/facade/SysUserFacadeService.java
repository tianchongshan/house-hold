package com.tcs.household.mgr.facade;

import cn.hutool.crypto.SecureUtil;
import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.dao.SystemUserInfoDao;
import com.tcs.household.mgr.dao.SystemUserRoleDao;
import com.tcs.household.mgr.model.request.UserRequest;
import com.tcs.household.persistent.entity.SystemUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

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
        userRoleDao.addUserRole(userInfo.getId(),user.getRoleId());
    }
}
