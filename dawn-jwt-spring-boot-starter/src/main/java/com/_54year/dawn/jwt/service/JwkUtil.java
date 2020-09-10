package com._54year.dawn.jwt.service;

/**
 * Json Web Key工具
 *
 * @author Andersen
 */
public class JwkUtil {
    /**
     * token前缀
     */
    public static String BEARER = "bearer";
    /**
     * token长度
     */
    public static Integer AUTH_LENGTH = 7;

    /**
     * 获取Token字符串
     *
     * @param authorization 签证信息
     * @return token
     */
    public static String getTokenStr(String authorization) {
        if (authorization != null && authorization.length() > AUTH_LENGTH) {
            String headStr = authorization.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                authorization = authorization.substring(7);
            }
            return authorization;
        } else {
            return null;
        }
    }


}
