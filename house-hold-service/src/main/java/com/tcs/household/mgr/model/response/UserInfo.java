package com.tcs.household.mgr.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by chongshan.tian01.
 */
@Data
public class UserInfo implements Serializable{

    private static final long serialVersionUID = -6458846657603061950L;

    private Integer userId;

    private String loginName;

    private String userName;

    private String mobileNo;

    private String email;

    private Integer[] roles;
}
