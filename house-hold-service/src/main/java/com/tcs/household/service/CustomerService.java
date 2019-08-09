package com.tcs.household.service;

import com.tcs.household.dao.CustomerDao;
import com.tcs.household.dto.response.CustomerResponse;
import com.tcs.household.persistent.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<CustomerResponse> queryCustomer() {
        List<Customer> customers = customerDao.queryAll();
        List<CustomerResponse> customerResponseList=new ArrayList<>();
        for (Customer customer :customers){
            CustomerResponse response=new CustomerResponse();
            response.setCustomCode(customer.getCustomCode());
            response.setCustomName(customer.getCustomName());
            response.setCustomType(customer.getCustomType());
            response.setChannel(customer.getChannel());
            response.setCreateTime(customer.getCreateTime());
            response.setUpdateTime(customer.getUpdateTime());
            customerResponseList.add(response);
        }
        return customerResponseList;
    }
}
