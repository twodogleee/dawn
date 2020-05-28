package net.sucx.dawn.admin.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "dawn-auth")
public interface AuthFeignClient {
    @RequestMapping(value = "/test/test1", method = RequestMethod.GET)
    String test1();

    @RequestMapping(value = "/test/test2", method = RequestMethod.POST)
    String test2(@RequestHeader("Authorization") String authorization);

}
