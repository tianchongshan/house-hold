package com.tcs.household.codeUtils.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author lushaozhong
 * @version 1.0
 * @date 2019-02-18 17:07
 */
public class SoaUtil {

    public static Boolean isSuccessCode(String returnCode) {
        return StrUtil.isBlank(returnCode)?Boolean.valueOf(false):(!"0".equals(returnCode) && !"000000".equals(returnCode)?Boolean.valueOf(false):Boolean.valueOf(true));
    }
}
