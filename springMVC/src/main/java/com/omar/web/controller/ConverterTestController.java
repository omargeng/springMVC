package com.omar.web.controller;

import com.omar.spring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2019/3/14
 * Description:
 */
@Controller
@RequestMapping("/ConverterTest")
public class ConverterTestController extends  AbstractController{

    //springMVC/ConverterTest/showUser?user=张三:12335:18566547898:45588889
    @RequestMapping("/showUser")
    public String getUser(@ModelAttribute("user") User user){
        log.info("---------------->user "+user);
//        modelMap.put("user", user);
        return "/user/queryResult";
    }
}
