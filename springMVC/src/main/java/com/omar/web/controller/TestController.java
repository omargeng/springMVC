package com.omar.web.controller;

import com.omar.spring.model.User;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/9/18
 * Description:测试专用
 */
@Controller
@RequestMapping("/test")
public class TestController extends AbstractController {

    @RequestMapping("/testMatrix1/{userId}")
    public String testMatrix(@PathVariable("userId") String userId, @MatrixVariable String a) {
        log.info("a======> " + a);
        return "/test/success";
    }

    // GET /testMatrix/42;q=11/pets/21;q=22

    @RequestMapping(value = "/testMatrix2/{userId}/pets/{petId}", method = RequestMethod.GET)
    public String findPet(
            @MatrixVariable(value = "q", pathVar = "userId") int q1,
            @MatrixVariable(value = "q", pathVar = "petId") int q2) {

        log.info("a======> " + q1);
        log.info("a======> " + q2);
        return "/test/success";
    }

    // GET /testMatrix/42;q=11;r=12/pets/21;q=22;s=23

    @RequestMapping(value = "/testMatrix3/{userId}/pets/{petId}", method = RequestMethod.GET)
    public String findPet(
            @MatrixVariable Map<Object, Object> matrixVars,
            @MatrixVariable(pathVar = "petId") Map<String, String> petMatrixVars) {

        // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
        // petMatrixVars: ["q" : 11, "s" : 23]
        log.info("a======> " + matrixVars);
        log.info("a======> " + petMatrixVars);
        return "/test/success";
    }

    @RequestMapping("/test1")
    public String test1(@RequestBody String body){
        log.info(body);
        return "test/success";
    }

    @ResponseBody
    @RequestMapping("/test2/{imageId}")
    public byte[] test2(@PathVariable String imageId, HttpServletRequest httpServletRequest) throws IOException {
        log.info("imageId===============>"+imageId);
        Resource resource=new ServletContextResource(httpServletRequest.getServletContext(),"/WEB-INF/images/tu.png");
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }

    @RequestMapping("/test3")
    public String test3(@RequestBody Map body){
        log.info(body);
        return "test/success";
    }

    @RequestMapping("/test4")
    public String test4(HttpEntity<String> httpEntity){
        log.info(httpEntity.getHeaders().get("user-agent"));
        log.info(httpEntity.getBody());
        return "test/success";
    }

    @RequestMapping("/test5")
    public ResponseEntity<byte[]> test5(@PathVariable String imageId, HttpServletRequest httpServletRequest) throws IOException {
        log.info("imageId===============>"+imageId);
        Resource resource=new ServletContextResource(httpServletRequest.getServletContext(),"/WEB-INF/images/tu.png");
        byte[] filedate=FileCopyUtils.copyToByteArray(resource.getInputStream());

        ResponseEntity<byte[]> responseEntity=new ResponseEntity<byte[]>(filedate, HttpStatus.OK);
        return responseEntity;
    }

    @ResponseBody
    @RequestMapping("/test6")
    public User test6(@RequestBody User user){
        log.info("--------------->user="+user);
        user.setUserId("24545");
        return user;
    }

    @RequestMapping("/test7")
    public String test7(Map body){
        log.info(body);
        return "test/success";
    }

    @ModelAttribute("user")
    public User getUser(){
        User user=new User();
        user.setUserName("@ModelAttribute");
        return user;
    }

}
