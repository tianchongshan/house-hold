package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.mgr.persistent.entity.SystemRolePermission;
import com.tcs.household.mgr.persistent.mapper.SystemRolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * 清除Role相关权限
     * @param roleId
     */
    public void removePermissionByRole(Integer roleId) {
        SystemRolePermission record = new SystemRolePermission();
        record.setRoleId(roleId);
        mapper.delete(record);

    }

    /**
     * 清除Perm相关数据
     * @param permId
     */
    public void removePermissionByPermId(Integer permId) {
        SystemRolePermission record = new SystemRolePermission();
        record.setPermissionId(permId);
        mapper.delete(record);
    }

    /**
     * 添加权限
     * @param roleId
     * @param perms
     */
    public void addRolePerm(Integer roleId, List<Integer> perms) {
        Date now = new Date();
        for (Integer permId : perms) {
            SystemRolePermission rp = new SystemRolePermission();
            rp.setRoleId(roleId);
            rp.setPermissionId(permId);
            rp.setCreateTime(now);
            mapper.insertSelective(rp);
        }
    }

    /**
     * 添加权限
     * @param permId
     * @param roles
     */
    public void addPermRole(Integer permId, List<Integer> roles) {
        Date now = new Date();
        for (Integer roleId : roles) {
            SystemRolePermission rp = new SystemRolePermission();
            rp.setRoleId(roleId);
            rp.setPermissionId(permId);
            rp.setCreateTime(now);
            mapper.insertSelective(rp);
        }
    }

    /**
     *
     * @param roleId
     * @return
     */
    public List<Integer> getPermByRoleId(Integer roleId) {
        SystemRolePermission record = new SystemRolePermission();
        record.setRoleId(roleId);
        List<SystemRolePermission> lisPerm = mapper.select(record);

        List<Integer> retList = new ArrayList<>();
        for (SystemRolePermission p : lisPerm) {
            retList.add(p.getPermissionId());
        }
        return retList;
    }

    /**
     *
     * @param permId
     * @return
     */
    public List<Integer> getRoleByPermId(Integer permId) {
        SystemRolePermission record = new SystemRolePermission();
        record.setPermissionId(permId);
        List<SystemRolePermission> lisPerm = mapper.select(record);
        List<Integer> retList = new ArrayList<>();
        for (SystemRolePermission p : lisPerm) {
            retList.add(p.getRoleId());
        }
        return retList;
    }
}
