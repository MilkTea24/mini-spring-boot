package com.milktea.myspring.boot.web;

public class AnnotatedBeanDefinitionReader {
    private BeanDefinitionRegistry registry;

    public void register(Class<?>... componentClasses) {
        for (Class<?> componentClass : componentClasses) {
            registerBean(componentClass);
        }
    }

    //registry에 빈을 등록
    public void registerBean(Class<?> beanClass) {

    }
}
