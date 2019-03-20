package com.omar.web.controller;

import com.omar.spring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
@RequestMapping("/FormatTest")
@Controller
public class FormatTestController extends AbstractController {

    @RequestMapping("/showUser")
    public String showUser(@ModelAttribute("user") User user){
        log.info("------------->user"+user);
        return "/user/queryResult";
    }

    @RequestMapping("/showUser1")
    public String showUser1(ModelMap modelMap){
        log.info("------------->modelMap"+modelMap);
        return "/user/queryResult";
    }
}
