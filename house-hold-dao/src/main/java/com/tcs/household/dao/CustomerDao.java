package com.tcs.household.dao;

import com.tcs.household.persistent.entity.Customer;
import com.tcs.household.persistent.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * create by chongshan.tian01
 **/
@Repository
public class CustomerDao extends BaseDao<Customer>{

    @Autowired
    private CustomerMapper mapper;

    @Override
    protected Mapper<Customer> getMapper() {
        return mapper;
    }
}
