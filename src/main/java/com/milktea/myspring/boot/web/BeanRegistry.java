package com.milktea.myspring.boot.web;

public interface BeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    boolean containsSingleton(Class<?> beanType);

    void destroySingleton(Class<?> beanType);

    public Object getSingleton(Class<?> beanType);
}
