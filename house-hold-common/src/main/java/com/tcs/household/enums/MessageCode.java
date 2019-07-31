package com.tcs.household.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public enum MessageCode {

    /*********************** 公共错误码10开头 *********************/
    SUCCESS("000000", "处理成功"),
    FAIL("100001", "系统异常"),
    REQUEST_PARAM_ERROR("100002", "请求参数不合法"),
    PAGING_PARAM_ERROR("100003", "分页参数不合法"),
    COMMON_CONFIG_NOT_EXIST("100004","公共配置信息不存在"),
    REMOTE_SERVICE_CALL_ERROR("100005","第三方服务调用异常"),
    REQUEST_PARAM_NULL_ERROR("100006", "必填请求参数为空"),
    OVER_RATE_LIMITER_ERROR("100007", "服务器请求过多，请稍后再试"),
    DATA_NOT_EXISTS_ERROR("100008", "数据不存在"),
    DATA_EXISTS_ERROR("100009", "数据已存在"),
    DATA_NAME_EXISTS_ERROR("100010", "名称已存在"),
    TOKEN_INVALID("100011", "Token无效或过期"),
    UPDATE_ERROR("100016", "数据更新失败"),
    WEB_USER_NOT_AUTH("100017", "用户认证失败"),

    USER_NOX_EXISTS("200000","用户不存在");
    private final String code;

    private final String message;
    MessageCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code 错误编码
     * @return 与code 对应的枚举值.
     */
    public static MessageCode getByCode(String code) {
        for (MessageCode value : values()) {
            if (StrUtil.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }
}
