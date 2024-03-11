package edu.pnu.myspring.utils;

import edu.pnu.myspring.annotations.MyRestController;
import edu.pnu.myspring.core.MyApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;

public class ControllerRegistry {
    private static final Class<? extends Annotation> CONTROLLER_ANNOTATION_CLASS = MyRestController.class;
    private final Map<Class<?>, Object> beanRegistry;

    public ControllerRegistry(MyApplicationContext context) {
        beanRegistry = Collections.unmodifiableMap(context.getBeans(CONTROLLER_ANNOTATION_CLASS));
    }

    public Map<Class<?>, Object> getControllerBeans() {
        return beanRegistry;
    }
}
