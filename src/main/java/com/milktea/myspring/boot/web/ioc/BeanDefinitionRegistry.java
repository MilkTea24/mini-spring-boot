package com.milktea.myspring.boot.web.ioc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanDefinitionRegistry {
    Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();

    public Map<Class<?>, BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public BeanDefinition getBeanDefinition(Class<?> type) {
        return beanDefinitions.get(type);
    }

    public void addAll(List<BeanDefinition> beanDefinitionsList) {
        for (int i = 0; i < beanDefinitionsList.size(); i++) {
            beanDefinitions.put(beanDefinitionsList.get(i).getBeanType(), beanDefinitionsList.get(i));
        }
    }
}
