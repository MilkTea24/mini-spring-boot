package com.milktea.myspring.boot.web;

public interface BeanFactory {
    void createBeans();

    BeanRegistry getBeanRegistry();

    BeanDefinitionRegistry getBeanDefinitionRegistry();
}
