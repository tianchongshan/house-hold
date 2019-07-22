package com.tcs.household.util;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public final class CommonUtils {

    /**
     * 取得客户端真实IP
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ipString = request.getHeader("x-forwarded-for");
        if (StrUtil.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }
        return ipString;
    }
}
