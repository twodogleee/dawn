package net.sucx.dawn.auth.controller;

import com.alibaba.fastjson.JSONObject;
import net.sucx.dawn.common.exception.JwtServiceException;
import net.sucx.dawn.common.jwt.JwkUtil;
import net.sucx.dawn.common.jwt.JwtService;
import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    JwtService jwtService;

    @GetMapping("/test1")
    public Object test1() throws JwtServiceException {
        String token = jwtService.createToken("abc123456");
        return token;
    }

    @PostMapping("/test2")
    public Object test2(@RequestHeader("Authorization") String authorization) throws JwtServiceException {
        JwtClaims claims = jwtService.parseToken(JwkUtil.getTokenStr(authorization));
        System.out.println(claims);
        return "验证成功user_id="+ claims.getClaimValue("user_id");
    }
}
