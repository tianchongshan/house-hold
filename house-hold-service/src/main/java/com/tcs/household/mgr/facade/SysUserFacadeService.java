package com.tcs.household.mgr.facade;

import cn.hutool.crypto.SecureUtil;
import com.tcs.household.codeUtils.service.CodeService;
import com.tcs.household.enums.LoginUserTypeEnums;
import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.dao.SystemUserInfoDao;
import com.tcs.household.mgr.dao.SystemUserRoleDao;
import com.tcs.household.mgr.model.request.UserRequest;
import com.tcs.household.persistent.entity.SystemUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

/**
 * Created by chongshan.tian01.
 */
@Slf4j
@Service
public class SysUserFacadeService {

    @Autowired
    private SystemUserInfoDao userInfoDao;

    @Autowired
    private SystemUserRoleDao userRoleDao;

    @Autowired
    private SysCommonDao sysCommonDao;

    private CodeService codeService;

    /**
     * 新增用户
     * @param user
     */
    public void saveUser(UserRequest user) {
        SystemUserInfo userInfo=new SystemUserInfo();
        userInfo.setLoginName(user.getLoginName());
        userInfo.setUserName(user.getUsername());
        String encryptPwd = SecureUtil.md5(user.getPassword() + user.getMobileNo());
        userInfo.setPassword(encryptPwd);
        userInfo.setMobileNo(user.getMobileNo());
        userInfo.setIsFreeze(0);
        userInfo.setUpdateTime(new Date());
        userInfo.setCreateTime(new Date());
        userInfo.setType(user.getType());
        userInfo.setFlag(0);
        userInfo.setUserCode("1");
        try {
            String s = codeService.generateCode("USER", "USERCODE", new Date(), null);
        }catch (Exception e){
            log.error(e+"");

        }
        //userInfo.setUserCode(codeService.generateCode("USER","USERCODE",new Date(),null));
        userInfoDao.saveSelective(userInfo);
        //userRoleDao.addUserRole(userInfo.getId(), user.getRoles());
    }
}
