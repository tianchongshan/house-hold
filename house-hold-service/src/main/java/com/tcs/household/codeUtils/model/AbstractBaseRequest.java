package com.tcs.household.codeUtils.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基础对象
 * @author lushaozhong
 * @Date 2019-02-20 10:39
 */
@Getter
@Setter
public abstract class AbstractBaseRequest implements Serializable {


    private static final long serialVersionUID = -62446049606149930L;
    /**
     * 当前登录的用户ID
     */
    private String baseUserId;

    /**
     * 当前登录的用户名(目前B端用)
     */
    private String baseLoginName;

    /**
     * 当前登录用户类型 1:C端用户  2:B端用户
     */
    private Integer baseUserType;

    /**
     * 当前登录合作伙伴编码(B端用)
     */
    private String basePartnerCode;

    /**
     * 请求源(C端用)
     */
    private String baseRequestSource;
    /**
     * app版本号(APP用)
     */
    private String baseAppVersion;
    /**
     * 客户来源渠道
     */
    private String baseChannel;
    /**
     * 客户端包名(APP端用)
     */
    private String baseAppIdentifier;

}
