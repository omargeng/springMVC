package com.omar.spring.web.servlet;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/4/28
 * Description:基于servlet3实现编码初始化spring容器，用于替换掉web.xml
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        servletContext.setInitParameter("spring.profiles.active","development");
        servletContext.setInitParameter("spring.profiles.default","development");
        servletContext.setInitParameter("spring.liveBeansView.mbeanDomain","development");

        //基于xml的bean配置
        servletContext.setInitParameter("contextConfigLocation","classpath:application.xml");

        //基于类注解的bean配置
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(AppConfig.class);
//        servletContext.addListener(new ContextLoaderListener(rootContext));

//      log4j日志配置
//        servletContext.setInitParameter("log4jConfigLocation","classpath:log4j.xml");
//        servletContext.addListener(new Log4jConfigListener());
//      logback的配置
        servletContext.setInitParameter("logbackConfigLocation","classpath:logback.xml");

        servletContext.addListener(new LogbackConfigListener());
        servletContext.addListener(new ContextLoaderListener());
        servletContext.addListener(new IntrospectorCleanupListener());

        ServletRegistration.Dynamic springServletRegistration = servletContext.addServlet("spring", new DispatcherServlet());
        springServletRegistration.setInitParameter("contextConfigLocation","classpath:webApplication.xml");
        springServletRegistration.setLoadOnStartup(1);
        springServletRegistration.addMapping("*.do");

        ServletRegistration.Dynamic druidServletRegistration = servletContext.addServlet("druid", new StatViewServlet());
        druidServletRegistration.addMapping("/druid/*");

        FilterRegistration encodFilterRegistration=servletContext.addFilter("characterEncodingFilter","org.springframework.web.filter.CharacterEncodingFilter");
        encodFilterRegistration.setInitParameter("encoding","UTF-8");
        encodFilterRegistration.setInitParameter("forceEncoding","true");
        encodFilterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

        FilterRegistration druidFilterRegistration=servletContext.addFilter("druidFilterRegistration","com.alibaba.druid.support.http.WebStatFilter");
        druidFilterRegistration.setInitParameter("exclusions","/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        druidFilterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

//        servletContext.setSessionTimeout(60);


    }
}
