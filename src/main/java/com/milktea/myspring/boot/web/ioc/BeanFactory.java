package com.milktea.myspring.boot.web.ioc;

public interface BeanFactory {
    void createBeans();

    BeanRegistry getBeanRegistry();

    BeanDefinitionRegistry getBeanDefinitionRegistry();
}
