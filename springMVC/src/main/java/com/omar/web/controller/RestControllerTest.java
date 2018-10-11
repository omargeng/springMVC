package com.omar.web.controller;

import com.omar.spring.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/9/21
 * Description:测试RestController注解的使用
 */
@RestController
public class RestControllerTest extends AbstractController {

    @RequestMapping("/api")
    public User api() throws InterruptedException {
        Thread.sleep(10L * 1000);
        User user = new User();
        user.setUserId("4566");
        user.setUserName("huii");
        user.setPhoneNo("18566253458");
        return user;
    }
}
