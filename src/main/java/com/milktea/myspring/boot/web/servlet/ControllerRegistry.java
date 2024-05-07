package com.milktea.myspring.boot.web.servlet;



import com.milktea.myspring.annotations.RestController;
import com.milktea.myspring.boot.web.ioc.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;

public class ControllerRegistry {
    private static final Class<? extends Annotation> CONTROLLER_ANNOTATION_CLASS = RestController.class;
    private final Map<Class<?>, Object> beanRegistry;

    public ControllerRegistry(ApplicationContext context) {
        beanRegistry = Collections.unmodifiableMap(context.getBeansWithAnnotation(CONTROLLER_ANNOTATION_CLASS));
    }

    public Map<Class<?>, Object> getControllerBeans() {
        return beanRegistry;
    }
}
