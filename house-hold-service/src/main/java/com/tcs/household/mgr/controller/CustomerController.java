package com.tcs.household.mgr.controller;

import com.tcs.household.dto.response.CustomerResponse;
import com.tcs.household.model.response.JsonResponse;
import com.tcs.household.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by chongshan.tian01
 **/
@Api(value = "customerController")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    @ApiOperation(value = "获得用户列表",notes = "查询数据库中的数据",httpMethod = "GET" ,response = List.class)
    public JsonResponse<List<CustomerResponse>> queryCustomerList(){
        return JsonResponse.success(customerService.queryCustomer());
    }
}
