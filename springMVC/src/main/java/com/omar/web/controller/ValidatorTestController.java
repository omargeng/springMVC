package com.omar.web.controller;

import com.omar.spring.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2019/3/14
 * Description:
 */
@RequestMapping("/ValidatorTest")
@Controller
public class ValidatorTestController extends AbstractController {

    @RequestMapping("/validate")
    public String validate(@Valid @ModelAttribute("user") User user, BindingResult result){
        log.info("-------------->"+user);

        if(result.hasErrors()){
            List<ObjectError> allerrors= result.getAllErrors();
            for(ObjectError error:allerrors){
                log.info("-------------->error"+error);
            }
            return "/user/queryError";
        }
        return "/user/queryResult";
    }
}
