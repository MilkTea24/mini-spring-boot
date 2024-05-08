package com.milktea.myspring.boot.web.ioc;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    boolean containsSingleton(Class<?> beanType);

    void destroySingleton(Class<?> beanType);

    Object getSingleton(Class<?> beanType);

    Map<Class<?>, Object> getSingletonsWithAnnotation(Class<? extends Annotation> annotationClass);
}
