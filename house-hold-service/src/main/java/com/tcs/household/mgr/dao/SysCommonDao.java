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

    public List<SystemPermissionInfo> getPermissonByLoginName(String userName) {
        return null;
    }

    public List<String> getUserRoleName(String userName) {
        return null;
    }
}
