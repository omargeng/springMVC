package com.omar.web.controller;

import com.omar.spring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("/viewTest")
@Controller
public class ViewTestController extends AbstractController {

    @RequestMapping("/jspViewTest")
    public String jspViewTest(ModelMap modelMap) {
        addUser(modelMap);
        return "/user/userList";
    }

    public void init(ModelMap modelMap) {
        Map sexMap = new HashMap();
        sexMap.put("1", "男");
        sexMap.put("2", "女");
        modelMap.put("sexMap", sexMap);

        Map cityMap = new HashMap();
        cityMap.put("beijing", "北京");
        cityMap.put("tianjin", "天津");
        cityMap.put("hebei", "河北");
        cityMap.put("shandong", "山东");
        cityMap.put("shanxi", "山西");
        modelMap.put("cityMap", cityMap);
    }

    @RequestMapping("/springFormTestPre")
    public String springFormTestPre(ModelMap modelMap, @ModelAttribute("user") User user) {
        init(modelMap);
        return "/user/userAdd";
    }

    @RequestMapping("/springFormTest")
    public String springFormTest(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            init(modelMap);
            return "/user/userAdd";
        } else {
            return "/user/userRegisterSuccess";
        }
    }

    @RequestMapping("/freemarkerTest")
    public String freemarkerTest(ModelMap modelMap) {
        addUser(modelMap);
        return "userList";
    }

    @RequestMapping("/excelTest")
    public String excelTest(ModelMap modelMap){
        addUser(modelMap);
        return "userListExcel";
    }

    @RequestMapping("/pdfTest")
    public String pdfTest(ModelMap modelMap){
        addUser(modelMap);
        return "userListPdf";
    }

    @RequestMapping("/xmlTest")
    public String xmlTest(ModelMap modelMap){
        addUser(modelMap);
        return "userListXml";
    }

    @RequestMapping("/jsonTest")
    public String jsonTest(ModelMap modelMap){
        addUser(modelMap);
        return "userListJson";
    }


    @RequestMapping("/contentNegotiatingTest")
    public String contentNegotiatingTest(ModelMap modelMap){
        addUser(modelMap);
        return "userList";
    }
    @RequestMapping(value = "/testxml")
    @ResponseBody
    public User testxml(){
        User user=creatUser("张三", "1584589876");
        return user;
    }

    @RequestMapping("/termview")
    public String gettrem(){
        return "term";
    }

    public void addUser(ModelMap modelMap){
        List<User> userList = new ArrayList<>();
        userList.add(creatUser("张三", "1584589876"));
        userList.add(creatUser("李四", "1584589876"));
        userList.add(creatUser("赵武", "1584589876"));
        modelMap.put("userList", userList);
    }

    public User creatUser(String userName, String phoneNo) {
        Random ra = new Random();
        User user = new User();
        user.setUserName(userName);
        user.setUserId(ra.nextInt(100) + "");
        user.setPhoneNo(phoneNo);
        user.setBirthDay(new Date());
        return user;
    }

}
