package net.sucx.dawn.gateway.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 网关常量类
 *
 * @author Andersen
 */
public class GatewayConstant {

    public final static String TOKEN_NAME = "Authorization";
    /**
     * 需要放行的urlList
     */
    public final static List<String> PASS_URL_LIST = Arrays.asList(
            "/login",
            "/aaa",
            "/bbb"
    );

}
