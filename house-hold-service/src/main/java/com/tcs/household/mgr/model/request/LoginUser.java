package com.tcs.household.mgr.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by chongshan.tian01.
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 3813196499666737946L;

    private String username;

    private String password;

}
