package com.tcs.household.mgr.controller;

import com.tcs.household.dto.response.CustomerResponse;
import com.tcs.household.model.response.JsonResponse;
import com.tcs.household.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by chongshan.tian01
 **/
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public JsonResponse<List<CustomerResponse>> queryCustomerList(){
        return JsonResponse.success(customerService.queryCustomer());
    }
}
