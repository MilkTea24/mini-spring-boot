package com.milktea.myspring.boot.web.ioc;

import java.lang.reflect.Field;
import java.util.Map;

public class AutowiredAnnotationBeanPostProcessor {
    private final BeanFactory beanFactory;

    public AutowiredAnnotationBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    public void setAutowired() {
        Map<Class<?>, BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitionRegistry().getBeanDefinitions();
        BeanRegistry beanRegistry = beanFactory.getBeanRegistry();

        try {
            for (BeanDefinition bd : beanDefinitions.values()) {
                Object bean = beanRegistry.getSingleton(bd.getBeanType());
                for (Field field : bd.getFieldDependencies()) {
                    Object dependantBean = beanRegistry.getSingleton(field.getType());
                    field.set(bean, dependantBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("빈 주입 중 문제가 발생하였습니다\n" + e.getMessage());
        }
    }
}
