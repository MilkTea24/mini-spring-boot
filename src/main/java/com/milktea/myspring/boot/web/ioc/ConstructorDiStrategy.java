package com.milktea.myspring.boot.web.ioc;

import java.util.List;
import java.util.Map;

public interface ConstructorDiStrategy {
    Map<Class<?>, Object> createBeans(Map<Class<?>, List<BeanDefinition>> beanGraph, Map<Class<?>, BeanDefinition> beanDefinitions);
}
