package com.omar.test.controller;

import com.omar.spring.model.User;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Collections;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/9/21
 * Description:AsyncRestTemplate异步无阻塞方式访问服务器
 */

public class AsyncRestTemplateTest {

    public static void main(String[] args){
        testXml();
    }


    public static void testXml(){
        AsyncRestTemplate restTemplate=buildAsyncRestTemplate();

        HttpHeaders httpheaders=new HttpHeaders();
        httpheaders.setContentType(MediaType.APPLICATION_XML);
        httpheaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        HttpEntity requestEntity=new HttpEntity(httpheaders);
        ListenableFuture<ResponseEntity<User>> future =restTemplate.postForEntity("http://localhost:8082/springMVC/api",requestEntity,User.class);


        future.addCallback(new ListenableFutureCallback<ResponseEntity<User>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("fail "+ex);
            }

            @Override
            public void onSuccess(ResponseEntity<User> result) {
                System.out.println("success "+result.getHeaders());
                System.out.println("success "+result.getBody());
            }
        });
        System.out.println("no wait");
    }


    public static AsyncRestTemplate buildAsyncRestTemplate(){
        AsyncRestTemplate restTemplate=new AsyncRestTemplate();
        //使用stream流化器，使用stax技术处理xml
        XStreamMarshaller xStreamMarshaller=new XStreamMarshaller();
        xStreamMarshaller.setAutodetectAnnotations(true);
        xStreamMarshaller.setStreamDriver(new StaxDriver());
        xStreamMarshaller.setAnnotatedClasses(User.class);
        //创建处理xml报文的HttpMessageConverter
        MarshallingHttpMessageConverter xmlConverter =new MarshallingHttpMessageConverter();
        xmlConverter.setMarshaller(xStreamMarshaller);
        xmlConverter.setUnmarshaller(xStreamMarshaller);

        restTemplate.getMessageConverters().add(xmlConverter);
        //创建处理json报文的HttpMessageConverter
        MappingJackson2HttpMessageConverter jsonConverter =new MappingJackson2HttpMessageConverter();

        restTemplate.getMessageConverters().add(jsonConverter);


        return restTemplate;
    }
}
