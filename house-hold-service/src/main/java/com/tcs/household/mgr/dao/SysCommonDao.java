package com.tcs.household.mgr.dao;

import com.tcs.household.mgr.persistent.mapper.SystemCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class SysCommonDao {

    @Autowired
    private SystemCommonMapper mapper;

}
