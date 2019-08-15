package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.mgr.persistent.mapper.SystemRoleMapper;
import com.tcs.household.persistent.entity.SystemUserRole;
import com.tcs.household.persistent.mapper.SystemUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 删除用户角色
     * @param userId
     */
    public void removeDataByUserId(Integer userId) {
        SystemUserRole record = new SystemUserRole();
        record.setUserId(userId);
        mapper.delete(record);
    }

    /**
     * 删除角色用户
     * @param roleId
     */
    public void removeDataByRoleId(Integer roleId) {
        SystemUserRole record = new SystemUserRole();
        record.setRoleId(roleId);
        mapper.delete(record);
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
    /**
     *
     * @param userId
     * @return
     */
    public Integer[] getUserRoles(Integer userId) {
        SystemUserRole record = new SystemUserRole();
        record.setUserId(userId);
        List<SystemUserRole> lisUserRole = mapper.select(record);
        List<Integer> lisRole = new ArrayList<>();
        for (SystemUserRole ur : lisUserRole) {
            lisRole.add(ur.getRoleId());
        }
        return lisRole.toArray(new Integer[lisRole.size()]);
    }

}
