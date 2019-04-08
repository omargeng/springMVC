package com.omar.test.controller;

import com.omar.spring.model.User;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/9/20
 * Description:
 */
public class TestControllerTest {
    @Test
    public void test1(){
        RestTemplate rt=new RestTemplate();
        MultiValueMap<String,String> form=new LinkedMultiValueMap<String, String>();
        form.add("userName","tom");
        form.add("password","111111");
        form.add("phoneNo","15464528645");
        rt.postForLocation("http://127.0.0.1:8082/springMVC/test/test1.do",form);
    }

    @Test
    public void test2() throws IOException {
        RestTemplate rt=new RestTemplate();
        byte[] bytes=rt.postForObject("http://127.0.0.1:8082/springMVC/test/test2/{imageId}.do",null,byte[].class,"1111");
        Resource resource=new FileSystemResource("H:/test.png");
        FileCopyUtils.copy(bytes,resource.getFile());
    }

    public RestTemplate buildRestTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        //使用stream流化器，使用stax技术处理xml
        XStreamMarshaller xStreamMarshaller=new XStreamMarshaller();
        xStreamMarshaller.setAutodetectAnnotations(true);
        xStreamMarshaller.setStreamDriver(new StaxDriver());
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

    @Test
    public void testXml(){
        RestTemplate restTemplate=buildRestTemplate();
        User user=new User();
        user.setPassword("45454");
        user.setPhoneNo("147569874756");
        user.setUserName("he he");

        HttpHeaders httpheaders=new HttpHeaders();
        httpheaders.setContentType(MediaType.APPLICATION_XML);
        httpheaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));

        HttpEntity<User> requestEntity=new HttpEntity<User>(user,httpheaders);
        ResponseEntity<String> responseEntity=restTemplate.exchange("http://localhost:8080/springMVC/viewTest/testxml", HttpMethod.POST,requestEntity,String.class);

        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());
//        User responseUser=responseEntity.getBody();
//        Assert.assertNotNull(responseUser);
//        Assert.assertEquals("5454545",responseUser.getUserId());

    }


    @Test
    public void testJson(){
        RestTemplate restTemplate=buildRestTemplate();
        User user=new User();
        user.setPassword("45454");
        user.setPhoneNo("147569874756");
        user.setUserName("he he");

        HttpHeaders httpheaders=new HttpHeaders();
        httpheaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpheaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        HttpEntity<User> requestEntity=new HttpEntity<User>(user,httpheaders);
        ResponseEntity<String> responseEntity=restTemplate.exchange("http://localhost:8080/springMVC/viewTest/testxml", HttpMethod.POST,requestEntity,String.class);

        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());
//        User responseUser=responseEntity.getBody();
//        Assert.assertNotNull(responseUser);
//        Assert.assertEquals("5454545",responseUser.getUserId());
    }

    public void testTypeResole(){

    }
}
