package com.milktea.myspring.boot.web.ioc;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface ApplicationContext {
    void refresh(String basePackage);

    void close();

    Map<Class<?>, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationClass);

    Object getBean(Class<?> clazz);
}
