package com.tcs.household.model.vo;

import java.io.Serializable;

/**
 * c端请求
 */
public class Session implements Serializable{

    private static final long serialVersionUID = 2189309490937502159L;
    /**
     * 用户Code
     */
    private String userCode;

    /**
     * 用户Token
     */
    private String token;
}
