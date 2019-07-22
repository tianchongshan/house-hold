package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.mgr.persistent.entity.SystemRolePermission;
import com.tcs.household.mgr.persistent.mapper.SystemRolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 角色授权信息dao
 */
@Repository
public class SystemRolePermissionDao extends BaseDao<SystemRolePermission> {
    @Autowired
    private SystemRolePermissionMapper mapper;
    @Override
    protected Mapper<SystemRolePermission> getMapper() {
        return mapper;
    }
}
