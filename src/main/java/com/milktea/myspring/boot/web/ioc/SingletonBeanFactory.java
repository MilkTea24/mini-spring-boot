package com.milktea.myspring.boot.web.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;

public class SingletonBeanFactory implements BeanFactory {
    private SingletonBeanRegistry instanceRegistry;

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    private ConstructorDiStrategy constructorDiStrategy;

    public SingletonBeanFactory(BeanDefinitionScanner scanner) {
        beanDefinitionRegistry = scanner.getBeanDefinitionRegistry();
        constructorDiStrategy = new TopologicalSortConstructorDiStrategy();
    }

    public SingletonBeanRegistry getBeanRegistry() {
        return instanceRegistry;
    }

    @Override
    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }

    @Override
    public void createBeans()  {
        Map<Class<?>, BeanDefinition> beanDefinitions = beanDefinitionRegistry.getBeanDefinitions();

        //현재 타입을 의존하는 BeanDefinition 들이 저장
        Map<Class<?>, List<BeanDefinition>> beanGraph = createBeanGraph(beanDefinitions);

        Map<Class<?>, Object> beans = new HashMap<>();

        //생성자 주입 빈들 생성
        if (!beanGraph.isEmpty()) beans = constructorDiStrategy.createBeans(beanGraph, beanDefinitions);

        //생성자 주입 아닌 빈들 생성
        for (Map.Entry<Class<?>, BeanDefinition> entry : beanDefinitions.entrySet()) {
            if (!beans.containsKey(entry.getKey())) beans.put(entry.getKey(), createInstance(entry.getValue()));
        }

        instanceRegistry = new DefaultSingletonBeanRegistry(beans);
    }

    private Object createInstance(BeanDefinition beanDefinition) {
        try {
            return beanDefinition.getBeanType().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("빈을 생성하는 중 문제가 발생하였습니다 : " + e.getMessage());
        }
    }

    private Map<Class<?>, List<BeanDefinition>> createBeanGraph(Map<Class<?>, BeanDefinition> beanDefinitions) {
        Map<Class<?>, List<BeanDefinition>> beanGraph = new HashMap<>();

        for (Class<?> clazz : beanDefinitions.keySet()) {
            beanGraph.put(clazz, new ArrayList<>());
        }

        for (BeanDefinition bd : beanDefinitions.values()) {
            List<Class<?>> constructorDependencies = bd.getConstructorDependencies();
            for (Class<?> clazz : constructorDependencies) {
                beanGraph.get(clazz).add(bd);
            }
        }

        return beanGraph;
    }

}
