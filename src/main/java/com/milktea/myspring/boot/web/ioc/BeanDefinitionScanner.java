package com.milktea.myspring.boot.web.ioc;

public interface BeanDefinitionScanner {
    void scan(String baseString);

    BeanDefinitionRegistry getBeanDefinitionRegistry();
}
