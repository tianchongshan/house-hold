package com.tcs.household.persistent.mapper;

import com.tcs.household.persistent.entity.SystemUserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * 系统管理用户信息
 * Created by chongshan.tian01.
 */
public interface SystemUserInfoMapper extends Mapper<SystemUserInfo> {


   @Select(value = "select * from `t_wm_system_user_info` where user_name=#{username} and flag = 0 ")
    SystemUserInfo getUserByLoginName(@Param("username") String userLoginName);
}
