package com.tcs.household.mgr.dao;

import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.mgr.persistent.entity.UserRoleEntity;
import com.tcs.household.mgr.persistent.mapper.SystemCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 取得用户菜单
     * @param loginName
     * @return
     */
    public List<SystemPermissionInfo> getMenuByLoginName(String loginName) {
        return mapper.getMenuByLoginName(loginName);
    }

    /**
     *
     * @return
     */
    public List<SystemPermissionInfo> getAllPermissions() {
        return mapper.getAllPermissions();
    }

    /**
     * 取得二级菜单、1级菜单对应关系
     * @return
     */
    public Map<Integer, Integer> getSecondMenus() {
        List<SystemPermissionInfo> secMenu = mapper.getSecondMenus();
        Map<Integer, Integer> mapSecMenu = new HashMap<>();
        for (SystemPermissionInfo p : secMenu) {
            mapSecMenu.put(p.getId(), p.getPid());
        }
        return mapSecMenu;
    }


    /**
     *
     * @param lisUserId
     * @return
     */
    public Map<Integer, String> getUserRoles(List<Integer> lisUserId) {
        Map<Integer, String> map = new LinkedHashMap<>();
        List<UserRoleEntity> list = mapper.getUserRoleNames(lisUserId);
        for (UserRoleEntity e : list) {
            map.put(e.getUserId(), e.getRoleName());
        }
        return map;
    }
}
