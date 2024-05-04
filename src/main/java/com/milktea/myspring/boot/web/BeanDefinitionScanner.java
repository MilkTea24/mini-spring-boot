package com.milktea.myspring.boot.web;

import java.util.Map;

public interface BeanDefinitionScanner {
    void scan(String baseString);

    BeanDefinitionRegistry getBeanDefinitionRegistry();
}
