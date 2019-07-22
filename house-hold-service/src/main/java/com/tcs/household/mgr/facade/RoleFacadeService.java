package com.tcs.household.mgr.facade;

import com.tcs.household.mgr.dao.SysCommonDao;
import com.tcs.household.mgr.dao.SystemRoleDao;
import com.tcs.household.mgr.dao.SystemRolePermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chongshan.tian01.
 */
@Service
public class RoleFacadeService  {

    @Autowired
    private SystemRoleDao roleDao;

    @Autowired
    private SysCommonDao commonDao;

    @Autowired
    private SystemRolePermissionDao rolePermissionDao;
}
