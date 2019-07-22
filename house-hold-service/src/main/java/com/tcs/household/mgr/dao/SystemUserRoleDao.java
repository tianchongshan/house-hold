package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.mgr.persistent.entity.SystemRole;
import com.tcs.household.mgr.persistent.mapper.SystemRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by chongshan.tian01.
 */
@Repository
public class SystemUserRoleDao extends BaseDao<SystemRole> {

    @Autowired
    private SystemRoleMapper mapper;
    @Override
    protected Mapper<SystemRole> getMapper() {
        return mapper;
    }
}
