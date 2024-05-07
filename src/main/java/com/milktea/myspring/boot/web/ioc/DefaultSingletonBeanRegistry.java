package com.milktea.myspring.boot.web.ioc;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;


/*
https://okky.kr/articles/563392
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //빈 instance를 빈의 타입으로 저장한다.
    private final Map<Class<?>, Object> singletonObjects;

    public DefaultSingletonBeanRegistry(Map<Class<?>, Object> singletonObjects) {
        this.singletonObjects = singletonObjects;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {

    }

    @Override
    public Object getSingleton(Class<?> beanType) {
        return singletonObjects.get(beanType);
    }

    @Override
    public boolean containsSingleton(Class<?> beanType) {
        return singletonObjects.containsKey(beanType);
    }

    @Override
    public void destroySingleton(Class<?> beanType) {
        singletonObjects.remove(beanType);
    }

    @Override
    public Map<Class<?>, Object> getSingletonsWithAnnotation(Class<? extends Annotation> annotationClass) {
        Map<Class<?>, Object> annotationBeans = new HashMap<>();

        for (Map.Entry<Class<?>, Object> entry : singletonObjects.entrySet()) {
            if (entry.getKey().isAnnotationPresent(annotationClass)) annotationBeans.put(entry.getKey(), singletonObjects.get(entry.getKey()));
        }

        return annotationBeans;
    }
}
