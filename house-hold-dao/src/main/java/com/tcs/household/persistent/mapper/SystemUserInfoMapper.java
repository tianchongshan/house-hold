package com.tcs.household.persistent.mapper;

import com.tcs.household.persistent.entity.SystemUserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * 系统管理用户信息
 * Created by chongshan.tian01.
 */
public interface SystemUserInfoMapper extends Mapper<SystemUserInfo> {


   @Select(value = "select * from `t_wm_system_user_info` where login_name=#{loginName} and flag = 0 ")
    SystemUserInfo getUserByLoginName(@Param("loginName") String userLoginName);

   @Update(value = "update `t_wm_system_user_info` set `last_login_ip`=#{requestIp} , `last_login_time`=now() where login_name=#{loginName} and `flag`=0")
    void saveUserLoginInfo(@Param("loginName")String loginName,@Param("requestIp") String requestIp);

}
