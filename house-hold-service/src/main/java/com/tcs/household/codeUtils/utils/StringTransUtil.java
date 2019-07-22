package com.tcs.household.codeUtils.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串转换处理工具类
 * @author lushaozhong
 * @version 1.0
 * @date 2018-06-28 10:21
 */
public class StringTransUtil {

    public static String transSensitiveInfo(String str, int preNum, int endNum){
        if (StrUtil.isEmpty(str) || str.length() <= preNum + endNum) {
            return str;
        }
        return str.replaceAll("(.{" + preNum +"})(.*)(.{" + endNum +"})", "$1" + "****" + "$3");
    }

}
