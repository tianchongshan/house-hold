package com.tcs.household.codeUtils.enums;

/**
 * @author lushaozhong
 * @version 1.0
 * @date 2017-11-07 14:21
 */
public enum SensitiveWordFilterEnum {
    /**
     * 最小匹配类型
     */
    MIN_MATCH_TYPE(0, "最小匹配"),
    /**
     * 最大匹配类型
     */
    MAX_MATCH_TYPE(1, "最大匹配")
    ;

    private int type;

    private String desc;

    private SensitiveWordFilterEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static SensitiveWordFilterEnum getByTyoe(int type) {
        for (SensitiveWordFilterEnum filterType : SensitiveWordFilterEnum.values()) {
            if (filterType.getType() == type) {
                return filterType;
            }
        }
        return null;
    }
}
