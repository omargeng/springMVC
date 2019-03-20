package com.omar.spring.converter;

import com.omar.spring.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2019/3/14
 * Description:将字符串转换为user对象的自定义转换器
 */
@Component("stringToUserConverter")
public class StringToUserConverter implements Converter<String, User> {
    @Override
    public User convert(String source) {
        User user=new User();
        if(source!=null){
            String[] items=source.split(":");
            if(items.length>=4){
                user.setUserName(items[0]);
                user.setUserId(items[1]);
                user.setPhoneNo(items[2]);
                user.setPassword(items[3]);
            }
        }
        return user;
    }
}
