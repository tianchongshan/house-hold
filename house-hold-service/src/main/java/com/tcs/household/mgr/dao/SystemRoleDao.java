package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.mgr.persistent.entity.SystemRole;
import com.tcs.household.mgr.persistent.mapper.SystemRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
 * 系统角色表
 */
@Repository
public class SystemRoleDao extends BaseDao<SystemRole> {

    @Autowired
    private SystemRoleMapper mapper;
    @Override
    protected Mapper<SystemRole> getMapper() {
        return mapper;
    }
}
