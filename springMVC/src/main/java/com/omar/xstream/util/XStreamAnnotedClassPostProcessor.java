package com.omar.xstream.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.*;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2017/6/16
 * Description:spring利用stream解析xml时，如果javabean使用了xstream的注解，需要给XStreamMarshaller配置annotatedClasses
 * 属性才能避免解析时找不到类别名与类的对应关系，如果多个类都使用了xstream的注解就需要把每个都配置的annotatedClasses中
 * 本后置处理器用于优化annotatedClasses的配置，只需要制定相关的包名，就可以将包以及子包下面所有标注了annotationName注解的类
 * 配置到XStreamMarshaller的annotatedClasses
 *
 */
public class XStreamAnnotedClassPostProcessor implements BeanPostProcessor {

    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private MetadataReaderFactory metadataReaderFactory =
            new CachingMetadataReaderFactory(this.resourcePatternResolver);
    private ArrayList<Class> annotedClass=new ArrayList<Class>();

    private String annotationName;//注解类名

    private final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();

    private final List<TypeFilter> excludeFilters = new LinkedList<TypeFilter>();

    private Map<String,String> include;//包含的类过滤器，key为过滤器类型，value为过滤器表达式，支持的过滤器类型参照component-scan的过滤器

    private Map<String,String> exclude;//不包含的类过滤器，key为过滤器类型，value为过滤器表达式，支持的过滤器类型参照component-scan的过滤器

    private String basePackage;//包名

    private boolean scanFlag=false;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(!scanFlag){
            findAnnotedClass();
            scanFlag=true;
        }
        if(bean.getClass().isAssignableFrom(XStreamMarshaller.class)){
            Class[] classes={};
            classes=annotedClass.toArray(classes);
            ((XStreamMarshaller)bean).setAnnotatedClasses(classes);

        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void findAnnotedClass(){
        String[] basePackages = StringUtils.tokenizeToStringArray(this.basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        for (String basePackage : basePackages) {
            basePackage=basePackage.replaceAll("\\.","/");
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    basePackage + "/"+DEFAULT_RESOURCE_PATTERN;
            try {
                Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                        if(isCandidateComponent(metadataReader)){
                            annotedClass.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        for (TypeFilter tf : this.excludeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return false;
            }
        }
        for (TypeFilter tf : this.includeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return true;
            }
        }
        return false;
    }

//    private boolean isConditionMatch(MetadataReader metadataReader) {
//
//        return metadataReader.getAnnotationMetadata().hasAnnotation(annotationName);
//    }

    protected TypeFilter createTypeFilter(String filterType, String expression) {
        ClassLoader classLoader=this.getClass().getClassLoader();
        try {
            if ("annotation".equals(filterType)) {
                return new AnnotationTypeFilter((Class<Annotation>) classLoader.loadClass(expression));
            }
            else if ("assignable".equals(filterType)) {
                return new AssignableTypeFilter(classLoader.loadClass(expression));
            }
            else if ("aspectj".equals(filterType)) {
                return new AspectJTypeFilter(expression, classLoader);
            }
            else if ("regex".equals(filterType)) {
                return new RegexPatternTypeFilter(Pattern.compile(expression));
            }
            else if ("custom".equals(filterType)) {
                Class<?> filterClass = classLoader.loadClass(expression);
                if (!TypeFilter.class.isAssignableFrom(filterClass)) {
                    throw new IllegalArgumentException(
                            "Class is not assignable to [" + TypeFilter.class.getName() + "]: " + expression);
                }
                return (TypeFilter) BeanUtils.instantiateClass(filterClass);
            }
            else {
                throw new IllegalArgumentException("Unsupported filter type: " + filterType);
            }
        }
        catch (ClassNotFoundException ex) {
            throw new FatalBeanException("Type filter class not found: " + expression, ex);
        }
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) throws ClassNotFoundException {
        this.annotationName = annotationName;
        ClassLoader classLoader=this.getClass().getClassLoader();
        includeFilters.add(new AnnotationTypeFilter((Class<Annotation>) classLoader.loadClass(annotationName)));
    }

    public ArrayList<Class> getAnnotedClass() {
        return annotedClass;
    }

    public void setAnnotedClass(ArrayList<Class> annotedClass) {
        this.annotedClass = annotedClass;
    }

    public Map<String, String> getInclude() {
        return include;
    }

    public void setInclude(Map<String, String> include) {
        this.include = include;
        Set<String> keySet=include.keySet();
        for(String filterType:keySet){
            String expression=include.get(filterType);
            TypeFilter includeTypeFilter=createTypeFilter(filterType,expression);
            includeFilters.add(includeTypeFilter);
        }
    }

    public Map<String, String> getExclude() {
        return exclude;
    }

    public void setExclude(Map<String, String> exclude) {
        this.exclude = exclude;
        Set<String> keySet=exclude.keySet();
        for(String filterType:keySet){
            String expression=exclude.get(filterType);
            TypeFilter excludeTypeFilter=createTypeFilter(filterType,expression);
            excludeFilters.add(excludeTypeFilter);
        }
    }
}
