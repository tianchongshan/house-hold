package com.tcs.household.model.request;

import com.tcs.household.model.vo.MgrUserInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class AbstractBaseRequest implements Serializable{

    private static final long serialVersionUID = -3836559279594725820L;

    /**
     * 登录用户信息
     */
    private MgrUserInfo LoginUserInfo;

}
