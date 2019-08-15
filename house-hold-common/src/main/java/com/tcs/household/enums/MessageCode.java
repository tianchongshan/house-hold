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

    REPEAT_OFFICER("100018","用户信息重复"),
    ID_CARD_WRONG("100018","身份证号格式不正确"),
    REQUEST_PARAM_INVALID("100020", "{}不合法"),
    REQUEST_PARAM_UPLOAD("100021", "请选择上传文件"),
    REQUEST_PARAM_UPLOAD_FORMAT("100022", "上传文件格式不正确"),

    /************************ 系统管理部分 **********************/
    Role_not_Exists("200001", "角色不存在"),
    Menu_not_Exists("200002", "菜单不存在"),
    User_not_Exists("200003", "用户不存在"),
    Menu_has_child("200004", "存在子项目，不能删除"),
    Role_Exists("200005", "角色已存在"),
    User_Exists("200006", "用户[{}]已存在"),
    REPEAT_COMMIT("200007", "重复请求，请稍后再试"),
    USER_NOX_EXISTS("100008", "用户不存在"),
    Agent_Exists("200009", "代理商[{}]已存在"),
    Channel_Exists("200010", "渠道名[{}]已存在"),
    Agent_NOT_Exists("200011","代理商[{}]不存在"),
    Channel_NOT_Exists("200012","渠道[{}]不存在"),
    AgentCode_NOT_Exists("200013","代理商编号[{}]不存在"),
    ChannelCode_NOT_Exists("200014","渠道编号[{}]不存在"),
    MobileNo_Exists("200015","电话号码[{}]已存在,请修改后添加"),
    Approve_NOT_Exists("200016","请填写拒绝理由"),
    Picture_Format_Wrong("200017","图片格式不正确"),
    Phone_Format_Wrong("200018","电话号码格式不正确"),
    IdCard_Exists("200019", "身份证号[{}]已存在"),
    FlaseField_IS_WRONG("200016","请传入正确的虚假原因"),
    /*********************** 系统错误码90开头 *********************/
    TOKEN_ERROR("900000", "Token异常,需重新登录"),
    NOT_PERMISSION_ACCESS("900001", "没有权限访问"),
    USER_FREEZED("900002", "用户被冻结"),
    USER_LOINGINFO_ERROR("900003", "用户名/密码错误"),
    Authentication_Failed("900004", "不允许访问"),
    Token_Refresh_Failed("900005", "Token刷新失败"),

    /*********************** 活动 *********************/
    ACTIVITY_NOT_EDIT("300001", "优惠券已经发放，不可编辑"),
    ;

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
