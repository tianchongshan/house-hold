package com.tcs.household.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MgrUserInfo implements Serializable {


    private static final long serialVersionUID = -4732048751712736306L;
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;
}
