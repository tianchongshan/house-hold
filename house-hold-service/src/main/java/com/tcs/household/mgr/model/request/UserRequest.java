package com.tcs.household.mgr.model.request;

import com.tcs.household.model.request.AbstractBaseRequest;
import lombok.Data;

/**
 * 新增用户
 * Created by chongshan.tian01.
 */
@Data
public class UserRequest extends AbstractBaseRequest {
    private static final long serialVersionUID = -8781046558236850502L;

    private Integer userId;

    private String loginName;

    private String userName;

    private String mobileNo;

    private String email;

    private Integer[] roles;
}
