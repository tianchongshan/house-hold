package com.tcs.household.dao;

import com.tcs.household.persistent.entity.SystemUserRole;
import com.tcs.household.persistent.mapper.SystemUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by chongshan.tian01.
 */
@Repository("baseSystemUserRoleDao")
public class SystemUserRoleDao extends BaseDao<SystemUserRole>{

    @Autowired
    private SystemUserRoleMapper mapper;

    @Override
    protected Mapper<SystemUserRole> getMapper() {
        return mapper;
    }
}
