package com.omar.spring.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/4/19
 * Description:自定义key生产器
 */
@Component("baseCacheKeyGenerator")
public class BaseCacheKeyGenerator implements KeyGenerator{
    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object key=new BaseCacheKey(target,method,params);
        return key.toString();
    }
}
