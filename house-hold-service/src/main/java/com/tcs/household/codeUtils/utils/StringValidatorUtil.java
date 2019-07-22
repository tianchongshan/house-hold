package com.tcs.household.codeUtils.utils;

import java.util.regex.Pattern;

/**
 * 字符串校验工具类
 *
 * @author lushaozhong
 * @version 1.0
 * @date 2018-05-28 17:49
 */
public class StringValidatorUtil {
    /**
     * 中文 数字 下划线
     */
    public static final Pattern RULE_NAME_PATTERN = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5][0-9_a-zA-Z\u4e00-\u9fa5]*$");
    public static final Pattern MOBILE_NO_PATTERN =
            Pattern.compile("^((13[0-9])|(14[5|7|9])|(15([0-3]|[5-9]))|(17[3|6|7|8])|(18[0-9])|(19[0-9]))\\d{8}$");
    public static final Pattern BANK_CARD_NO_PATTERN = Pattern.compile("^[1-9]{1}\\d{15,19}$");
    /**
     * 中文姓名正则：只能是中文，长度为2-7位
     */
    public static final Pattern CHINESE_NAME_PATTERN= Pattern.compile("^([\u4e00-\u9fa5]){2,7}$");
    public static final Pattern MOBILE_VNO =
            Pattern.compile("^(17[0|1|2|4|5|9])\\d{8}$");
    public static final Pattern NUMBER_STRING_LIST =
            Pattern.compile("^([1-9]{1}[,]{1})*([1-9]{1})?$");
    public static final Pattern THRESHOLD_NUMBER_STRING_LIST =
            Pattern.compile("^(0[,]{1})(\\d+[,]{1}){2}(\\d+)$");
    public static final Pattern NUMBER_STRING =
            Pattern.compile("^(0)|([-]?([1-9]{1})([0-9])*)$");
    public static final Pattern BOOLEAN_STRING =
            Pattern.compile("^(true)|(false)$");
    public static final Pattern FIELD_NAME =
            Pattern.compile("^[a-zA-Z_][0-9a-zA-Z_]{1,49}$");
    public static final Pattern STRATEGY_NAME =
            Pattern.compile("([\u4e00-\u9fa5]|[a-zA-Z0-9_])*$");
    public static final Pattern DOUBLE_STRING =
            Pattern.compile("^-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?$");
    public static final Pattern IMAGE_URL_STRING =
            Pattern.compile("^http://.+(\\.jpg|\\.png)$");
    public static final Pattern CODE_STRING =
            Pattern.compile("[a-zA-Z0-9_\\-\\.]]+");
    public static final Pattern SPECIAL_STRING_PATTERN =
            Pattern.compile("([\\$\\^\\*\\+=\\|\\{\\}\\[\\]<>])");

    /**
     * 校验规则名称以中文数字或下划线组成
     * @param str 银行卡号字符串
     * @return boolean
     */
    public static boolean validatorRuleNameString(String str){
        return RULE_NAME_PATTERN.matcher(str).matches();
    }

    /**
     * 匹配Code
     * @param str
     * @return
     */
    public static boolean validateCodeString(String str) {
        return CODE_STRING.matcher(str).matches();
    }

    /**
     * 校验中文姓名 只能是中文,长度为2-7位
     *
     * @param name 姓名
     * @return boolean
     */
    public static boolean validatorChineseName(String name){
        return CHINESE_NAME_PATTERN.matcher(name).matches();
    }

    /**
     * 校验手机号(以13,14,15,17,18开头的11位数字,详见正则)
     *
     * @param str 手机号字符串
     * @return boolean
     */
    public static boolean validatorMobileNo(String str){
        return MOBILE_NO_PATTERN.matcher(str).matches();
    }

    /**
     * 校验手机号(是否为虚拟运营商,详见正则)
     *
     * @param str 手机号字符串
     * @return boolean
     */
    public static boolean validatorMobileVNO(String str){
        return MOBILE_VNO.matcher(str).matches();
    }

    /**
     * 简易校验银行卡号格式:不以0开头的13-23位数字
     * @param str 银行卡号字符串
     * @return boolean
     */
    public static boolean validatorBankCardNo(String str){
        return BANK_CARD_NO_PATTERN.matcher(str).matches();
    }

    /**
     * 校验数字以逗号分隔的字符串列表:1,2,3,
     * @param str 银行卡号字符串
     * @return boolean
     */
    public static boolean validatorNumberListString(String str){
        return NUMBER_STRING_LIST.matcher(str).matches();
    }

    /**
     * 校验4个数字以逗号分隔的字符串列表:0,20,30,100
     * @param str 银行卡号字符串
     * @return boolean
     */
    public static boolean validatorThresholdNumberListString(String str){
        return THRESHOLD_NUMBER_STRING_LIST.matcher(str).matches();
    }

    /**
     * 校验数字字符串
     * @param str 字符串
     * @return boolean
     */
    public static boolean validatorNumberString(String str){
        return NUMBER_STRING.matcher(str).matches();
    }

    /**
     * 校验布尔类型字符串
     * @param str 字符串
     * @return boolean
     */
    public static boolean validatorBooleanString(String str){
        return BOOLEAN_STRING.matcher(str).matches();
    }

    /**
     * 校验字段名称
     * @param str
     * @return
     */
    public static boolean validatorFieldName(String str) {
        return FIELD_NAME.matcher(str).matches();
    }

    /**
     * 校验策略名称
     * @param str
     * @return
     */
    public static boolean validatorStrategyName(String str) {
        return STRATEGY_NAME.matcher(str).matches();
    }

    /**
     * 校验小数类型字符串
     * @param str
     * @return
     */
    public static boolean validatorDoubleString(String str) {
        return DOUBLE_STRING.matcher(str).matches();
    }

    /**
     * 校验图片URL字符串
     * @param str
     * @return
     */
    public static boolean validatorImageUrlString(String str) {
        return IMAGE_URL_STRING.matcher(str).matches();
    }

    /**
     * 校验是否包含特殊字符串
     * @param str
     * @return
     */
    public static boolean validatorContainSpecialString(String str){
        return SPECIAL_STRING_PATTERN.matcher(str).find();
    }

}
