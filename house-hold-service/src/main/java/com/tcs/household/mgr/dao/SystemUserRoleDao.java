package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.mgr.persistent.mapper.SystemRoleMapper;
import com.tcs.household.persistent.entity.SystemUserRole;
import com.tcs.household.persistent.mapper.SystemUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.Date;

/**
 * Created by chongshan.tian01.
 */
@Repository
public class SystemUserRoleDao extends BaseDao<SystemUserRole> {

    @Autowired
    private SystemUserRoleMapper mapper;
    @Override
    protected Mapper<SystemUserRole> getMapper() {
        return mapper;
    }

    /**
     * 添加用户及对应角色
     * @param id
     * @param roleId
     */
    public void addUserRole(Integer id,Integer roleId) {
        Date now = new Date();
        SystemUserRole systemUserRole = new SystemUserRole();
        systemUserRole.setRoleId(roleId);
        systemUserRole.setUserId(id);
        systemUserRole.setCreateTime(now);
        mapper.insertSelective(systemUserRole);
    }
}
