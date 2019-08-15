package com.tcs.household.mgr.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by chongshan.tian01
 **/
@Data
public class LoginUserInfo implements Serializable {

    private static final long serialVersionUID = 8870735211106315573L;

    private String loginName;

    private String userName;

    private boolean hasTopRule;

    private List<MenuInfo> menu;

    private List<String> permissions;
}
