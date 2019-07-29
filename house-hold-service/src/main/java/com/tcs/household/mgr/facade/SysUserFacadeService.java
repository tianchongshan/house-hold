package com.tcs.household.mgr.facade;

import com.tcs.household.codeUtils.service.CodeService;
import com.tcs.household.enums.LoginUserTypeEnums;
import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.dao.SystemUserInfoDao;
import com.tcs.household.mgr.dao.SystemUserRoleDao;
import com.tcs.household.mgr.model.request.UserRequest;
import com.tcs.household.persistent.entity.SystemUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

/**
 * Created by chongshan.tian01.
 */
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
        userInfo.setUserName(user.getUserName());
        userInfo.setMobileNo(user.getMobileNo());
        userInfo.setIsFreeze(0);
        userInfo.setUpdateTime(new Date());
        userInfo.setCreateTime(new Date());
        userInfo.setType(1);
        userInfo.setType(LoginUserTypeEnums.customer.getType());
        userInfo.setFlag(0);
        userInfo.setUserCode("1");
        //userInfo.setUserCode(codeService.generateCode("USER","USERCODE",new Date(),null));
        userInfoDao.saveSelective(userInfo);
        //userRoleDao.addUserRole(userInfo.getId(), user.getRoles());
    }
}
