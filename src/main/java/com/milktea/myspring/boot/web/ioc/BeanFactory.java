package com.milktea.myspring.boot.web.ioc;

public interface BeanFactory {
    void createBeans();

    SingletonBeanRegistry getBeanRegistry();

    BeanDefinitionRegistry getBeanDefinitionRegistry();
}
