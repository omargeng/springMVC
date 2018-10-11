package com.omar.web.controller;

import com.omar.spring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/9/26
 * Description:@SessionAttributes注解测试
 */

@Controller
@SessionAttributes("user")
@RequestMapping("/SessionTest")
public class SessionAttributesTestController extends AbstractController {

    @RequestMapping("/getUser")
    public String getUserPre(@ModelAttribute("user") User user){
        log.info("user========>"+user);
        user.setUserName("sessionTest");
        log.info(user);
        return "redirect:/SessionTest/showUser";
    }

    @RequestMapping("/showUser")
    public String showUser(HttpSession session, Map reqMap){
        log.info("reqMap======>"+reqMap);
        User user= (User) session.getAttribute("user");
        log.info("session user=========>"+user);
        return "/user/queryResult";
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        User user=new User();
        user.setUserName("login");
        user.setPhoneNo("12548658958");
        user.setUserId("1252");
        user.setPassword("5666845266");
        ModelAndView mv=new ModelAndView("/test/success");
        mv.addObject("user",user);
        return mv;
    }
}
