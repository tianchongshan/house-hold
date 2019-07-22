package com.tcs.household.mgr.dao;

import com.tcs.household.dao.BaseDao;
import com.tcs.household.persistent.entity.SystemUserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public class SystemUserInfoDao extends BaseDao<SystemUserInfo> {
    @Override
    protected Mapper<SystemUserInfo> getMapper() {
        return null;
    }
}
