package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.persistent.entity.SystemUserInfo;
import com.tcs.household.persistent.mapper.SystemUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Repository("systemUserInfoDao")
public class SystemUserInfoDao extends BaseDao<SystemUserInfo> {

    @Autowired
    private SystemUserInfoMapper mapper;

    @Override
    protected Mapper<SystemUserInfo> getMapper() {
        return mapper;
    }

    /**
     * 保存用户登录信息
     */
    public void saveUserLoginInfo(String loginName,String requestIp){
        SystemUserInfo user=new SystemUserInfo();
        user.setLastLoginIp(requestIp);
        user.setLastLoginTime(new Date());
        Example example=new Example(SystemUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flag",0)
                .andEqualTo("loginName",loginName);
        mapper.updateByExampleSelective(user,example);
    }
}
