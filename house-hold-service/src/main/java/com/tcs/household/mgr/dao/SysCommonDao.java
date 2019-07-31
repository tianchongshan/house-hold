package com.tcs.household.mgr.dao;

import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.mgr.persistent.mapper.SystemCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class SysCommonDao {

    @Autowired
    private SystemCommonMapper mapper;


    /**
     * 取得用户角色名
     * @param loginName
     * @return
     */
    public List<String> getUserRoleName(String loginName) {
        return mapper.getUserRoles(loginName);
    }

    /**
     * 取得用戶權限
     * @param loginName
     * @return
     */
    public List<SystemPermissionInfo> getPermissonByLoginName(String loginName) {
        return mapper.getPermissonByLoginName(loginName);
    }
}
