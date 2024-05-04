package com.milktea.myspring.boot.web;

import java.util.HashMap;
import java.util.Map;


/*
https://okky.kr/articles/563392
 */
public class DefaultSingletonBeanRegistry {
    //빈 instance를 빈의 타입으로 저장한다.
    private final Map<Class<?>, Object> singletonObjects;

    public DefaultSingletonBeanRegistry(Map<Class<?>, Object> singletonObjects) {
        this.singletonObjects = singletonObjects;
    }

    public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {

    }

    public Object getSingleton(Class<?> beanType) {
        return singletonObjects.get(beanType);
    }

    public boolean containsSingleton(Class<?> beanType) {
        return singletonObjects.containsKey(beanType);
    }

    public void destroySingleton(Class<?> beanType) {
        singletonObjects.remove(beanType);
    }

}
