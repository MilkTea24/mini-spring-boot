package com.milktea.myspring.boot.web.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;

public class TopologicalSortConstructorDiStrategy implements ConstructorDiStrategy {
    @Override
    public Map<Class<?>, Object> createBeans(Map<Class<?>, List<BeanDefinition>> beanGraph, Map<Class<?>, BeanDefinition> beanDefinitions) {
        Map<Class<?>, Long> inDegree = new HashMap<>();

        for (Map.Entry<Class<?>, List<BeanDefinition>> entry : beanGraph.entrySet()) {
            if (!inDegree.containsKey(entry.getKey())) inDegree.put(entry.getKey(), 0L);

            for (BeanDefinition bd : entry.getValue()) {
                if (!inDegree.containsKey(bd.getBeanType())) inDegree.put(bd.getBeanType(), 1L);
                else inDegree.put(bd.getBeanType(), inDegree.get(bd.getBeanType()) + 1L);
            }
        }

        Map<Class<?>, Object> beans = new HashMap<>();
        Queue<Class<?>> queue = new LinkedList<>();

        System.out.println(inDegree);

        for (Map.Entry<Class<?>, Long> entry : inDegree.entrySet()) {
            if (entry.getValue() != 0) continue;

            queue.add(entry.getKey());
            inDegree.put(entry.getKey(), -1L);
        }

        while (!queue.isEmpty()) {
            Class<?> current = queue.poll();
            System.out.println("current : " + current);

            beans.put(current, createBean(beans, beanDefinitions.get(current)));

            List<BeanDefinition> currentBeanDependencies = beanGraph.get(current);

            for (BeanDefinition bd : currentBeanDependencies) {
                inDegree.put(bd.getBeanType(), inDegree.get(bd.getBeanType()) - 1);
            }

            for (Map.Entry<Class<?>, Long> entry : inDegree.entrySet()) {
                if (entry.getValue() != 0) continue;

                queue.add(entry.getKey());
                inDegree.put(entry.getKey(), -1L);
            }
        }

        System.out.println(inDegree);

        //사이클 검사
        for (Map.Entry<Class<?>, Long> entry : inDegree.entrySet()) {
            if (entry.getValue() == -1) continue;

            throw new RuntimeException("순환 참조가 발생하였습니다.");
        }

        return beans;
    }

    private Object createBean(Map<Class<?>, Object> beans, BeanDefinition current) {
        try {
            Constructor<?> autowiredConstructor = current.getAutowiredConstructor();
            if (autowiredConstructor == null) autowiredConstructor = current.getBeanType().getDeclaredConstructor();

            Parameter[] constructorParameters = autowiredConstructor.getParameters();
            List<Object> arguments = new ArrayList<>();

            for (Parameter parameter : constructorParameters) {
                arguments.add(beans.get(parameter.getType()));
            }

            return autowiredConstructor.newInstance(arguments.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("빈 생성 시 문제가 발생하였습니다.");
        }
    }
}
