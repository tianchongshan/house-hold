package com.tcs.household.mgr.facade;

import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemPermissionInfoDao;
import com.tcs.household.mgr.dao.SystemRolePermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chongshan.tian01.
 */
@Service
public class MenuFacadeService {

    @Autowired
    private SysCommonDao sysCommonDao;

    @Autowired
    private SystemPermissionInfoDao systemPermissionInfoDao;

    @Autowired
    private SystemRolePermissionDao systemRolePermissionDao;
}
