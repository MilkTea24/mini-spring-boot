package com.milktea.myspring.boot.web.ioc;

import java.util.Map;

public class CommonAnnotationBeanPostProcessor {
    private BeanFactory beanFactory;

    public CommonAnnotationBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void postConstruct() {
        Map<Class<?>, BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitionRegistry().getBeanDefinitions();
        BeanRegistry beanRegistry = beanFactory.getBeanRegistry();

        for (BeanDefinition bd : beanDefinitions.values()) {
            if (bd.hasPostConstructMethod()) evokePostConstruct(bd, beanRegistry.getSingleton(bd.getBeanType()));
        }

    }

    public void preDestroy() {
        Map<Class<?>, BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitionRegistry().getBeanDefinitions();
        BeanRegistry beanRegistry = beanFactory.getBeanRegistry();

        for (BeanDefinition bd : beanDefinitions.values()) {
            if (bd.hasPreDestroyMethod()) evokePreDestroy(bd, beanRegistry.getSingleton(bd.getBeanType()));
        }
    }

    private void evokePostConstruct(BeanDefinition bd, Object instance) {
        try {
            bd.getPostConstructMethod().invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("PostConstruct를 실행하는 중 문제가 발생하였습니다." + e.getMessage());
        }
    }

    private void evokePreDestroy(BeanDefinition bd, Object instance) {
        try {
            bd.getPreDestroyMethod().invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("PreDestroy를 실행하는 중 문제가 발생하였습니다." + e.getMessage());
        }
    }
}
