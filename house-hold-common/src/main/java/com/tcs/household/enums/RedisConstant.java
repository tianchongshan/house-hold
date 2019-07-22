package com.tcs.household.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RedisKey
 */
@Getter
@AllArgsConstructor
public enum RedisConstant {

    KEYPATH_IAMGE("CREDIT_YOU:SMS:IMAGEPASSCODE:", "H5登录风控验证"),

    SMS_VALIDATE_CODE("CREDIT_YOU:SMS:VALIDATECODE:", "H5登录短信验证码"),

    REDIS_H5_LOGIN_TOKEN("CREDIT_YOU:H5:LOGIN:", "C端登陆Session"),

    REDIS_USER_LOGIN_TOKEN("CREDIT_YOU:LOGIN:", "管理后台登录Session"),
    ;
    private String key;

    private String remark;


}
