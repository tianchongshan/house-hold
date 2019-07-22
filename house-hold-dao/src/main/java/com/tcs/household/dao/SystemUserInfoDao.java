package com.tcs.household.dao;

import com.tcs.household.persistent.entity.SystemUserInfo;
import com.tcs.household.persistent.mapper.SystemUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Repository("baseSystemUserInfoDao")
public class SystemUserInfoDao extends BaseDao<SystemUserInfo> {

    @Autowired(required=false)
    private SystemUserInfoMapper mapper;

    @Override
    protected Mapper<SystemUserInfo> getMapper() {
        return mapper;
    }


}
