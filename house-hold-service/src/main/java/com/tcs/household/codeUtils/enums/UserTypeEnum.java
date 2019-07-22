package com.tcs.household.codeUtils.enums;

/**
 * @author lushaozhong
 * @version 1.0
 * @date 2019-02-20 11:01
 */
public enum UserTypeEnum {
    /**
     * C端用户
     */
    CLIENT(1, "C端用户"),
    /**
     * B端用户
     */
    INNER(2, "内部管理用户"),
    /**
     * 外部用户
     */
    EXTERNAL(3, "外部管理用户"),
    ;

    private int type;

    private String desc;

    private UserTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static UserTypeEnum getByType(int type) {
        for (UserTypeEnum userType : UserTypeEnum.values()) {
            if (userType.getType() == type) {
                return userType;
            }
        }
        return null;
    }
}
