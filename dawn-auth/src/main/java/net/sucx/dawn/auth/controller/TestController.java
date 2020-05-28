package net.sucx.dawn.auth.controller;

import net.sucx.dawn.common.exception.JwtServiceException;
import net.sucx.dawn.common.jwt.JwkUtil;
import net.sucx.dawn.common.jwt.JwtService;
import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    JwtService jwtService;

    @GetMapping("/login")
    public Map<String, Object> test1(@PathParam("loginName") String loginName, HttpServletResponse httpServletResponse) throws JwtServiceException {
    	Map<String,Object> userInfo = new HashMap<>();
    	userInfo.put("login_name",loginName);
    	userInfo.put("user_name","李二狗");
    	userInfo.put("role",new String[]{"admin","student"});
        String token = jwtService.createToken(userInfo);
        Map<String, Object> result = new HashMap<>();
        result.put("Authorization", "Bearer " + token);
        result.put("msg", "登录成功");
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        return result;
    }


    @PostMapping("/test2")
    public Object test2(@RequestHeader("Authorization") String authorization) throws JwtServiceException {
        JwtClaims claims = jwtService.parseToken(JwkUtil.getTokenStr(authorization));
        System.out.println(claims.getRawJson());
//        return "验证成功user_id=" + claims.getClaimValue("user_id");
		return "验证成功附加内容为"+claims.getRawJson();
    }

    @GetMapping("/test3")
    public Object test3() {
        return "我是需要被拦截的内容";
    }
}
