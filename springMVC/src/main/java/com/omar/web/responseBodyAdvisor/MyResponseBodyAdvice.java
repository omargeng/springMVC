package com.omar.web.responseBodyAdvisor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    Log log= LogFactory.getLog(MyResponseBodyAdvice.class);
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("respons====="+o);
        mediaType=MediaType.valueOf("application/xml;charset=UTF-8");
        return o;
    }
}
