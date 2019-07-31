package com.tcs.household.mgr.persistent.mapper;

import com.tcs.household.mgr.persistent.entity.SystemPermissionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemCommonMapper {

    List<String> getUserRoles(@Param("loginName") String loginName);

    List<SystemPermissionInfo> getPermissonByLoginName(@Param("loginName")String loginName);
}
