package com.omar.web.controller;

import com.omar.spring.model.User;
import com.omar.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2017/6/13
 * Description:
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    @RequestMapping("/registerPre")
    public String register(){
        return "/user/userRegister";
    }

    @RequestMapping("/register")
    public ModelAndView addUser(User user){
        userService.addUsre(user);
        ModelAndView ma=new ModelAndView();
        ma.setViewName("/user/userRegisterSuccess");

        return ma;
    }

    @RequestMapping("/userQueryPre")
    public String userQueryPre(){
        return "/user/userQuery";
    }

    @RequestMapping("/userQuery")
    public ModelAndView userQuery(String userId){
        User user=userService.queryUserById(userId);
        return new ModelAndView("/user/queryResult","user",user);
    }

    @RequestMapping("/{userId}")
    public String queryUserById(@PathVariable("userId") String userId){
        log.info("userid======>"+userId);
        return "/test/success";
    }

    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
