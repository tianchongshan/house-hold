package com.tcs.household.mgr.persistent.mapper;

import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import com.tcs.household.mgr.persistent.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemCommonMapper {

    List<String> getUserRoles(@Param("loginName") String loginName);

    List<SystemPermissionInfo> getPermissonByLoginName(@Param("loginName")String loginName);
    /**
     *
     * @param loginName
     * @return
     */
    List<SystemPermissionInfo> getMenuByLoginName(@Param("loginName") String loginName);
    /**
     *
     * @return
     */
    List<SystemPermissionInfo> getAllPermissions();

    /**
     *
     * @return
     */
    List<SystemPermissionInfo> getSecondMenus();

    /**
     *
     * @return
     */
    List<UserRoleEntity> getUserRoleNames(@Param("lisUserId") List<Integer> lisUserId);
}
