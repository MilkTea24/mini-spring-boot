package com.milktea.myspring.boot.web;

import com.milktea.myspring.annotations.Autowired;
import com.milktea.myspring.annotations.PostConstruct;
import com.milktea.myspring.annotations.PreDestroy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
    private Class<?> beanType;

    private Constructor<?> autowiredConstructor;

    private List<Class<?>> constructorDependencies = new ArrayList<>();

    private List<Field> fieldDependencies = new ArrayList<>();

    private List<Class<?>> setterDependencies = new ArrayList<>();
    
    private Method postConstructMethod;

    private Method preDestroyMethod;

    public BeanDefinition(Class<?> clazz) {
        beanType = clazz;

        Field[] fields = clazz.getDeclaredFields();
        addFieldDependencies(fields);

        Constructor<?>[] constructors = clazz.getConstructors();
        addConstructorDependencies(constructors);

        Method[] methods = clazz.getDeclaredMethods();
        addPostConstructMethod(methods);
        addPreDestroyMethod(methods);
    }

    public Method getPostConstructMethod() {
        return postConstructMethod;
    }

    public Method getPreDestroyMethod() {
        return preDestroyMethod;
    }

    public boolean hasPostConstructMethod() {
        return postConstructMethod != null;
    }

    public boolean hasPreDestroyMethod() {
        return preDestroyMethod != null;
    }

    public boolean isAutowiredPresent() {
        return !(constructorDependencies.isEmpty() && fieldDependencies.isEmpty());
    }

    public List<Class<?>> getConstructorDependencies() {
        return constructorDependencies;
    }

    public Constructor<?> getAutowiredConstructor() {
        return autowiredConstructor;
    }

    public List<Field> getFieldDependencies() {
        return fieldDependencies;
    }

    public boolean hasConstructorDependencies() {
        return !constructorDependencies.isEmpty();
    }

    public boolean hasFieldDependencies() {
        return !fieldDependencies.isEmpty();
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    private void addPostConstructMethod(Method[] methods) {
        boolean existOnlyOne = false;
        for (Method method : methods) {
            if (!method.isAnnotationPresent(PostConstruct.class)) continue;

            if (existOnlyOne) throw new RuntimeException("PostConstruct 어노테이션은 오직 하나만 있어야 합니다.");
            existOnlyOne = true;
            postConstructMethod = method;
        }
    }

    private void addPreDestroyMethod(Method[] methods) {
        boolean existOnlyOne = false;
        for (Method method : methods) {
            if (!method.isAnnotationPresent(PreDestroy.class)) continue;

            if (existOnlyOne) throw new RuntimeException("PreDestroy 어노테이션은 오직 하나만 있어야 합니다.");
            existOnlyOne = true;
            preDestroyMethod = method;
        }
    }
    private void addFieldDependencies(Field[] fields) {
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) continue;

            fieldDependencies.add(field);
        }
    }

    private void addConstructorDependencies(Constructor<?>[] constructors){
        //생성자가 하나면 Autowired 어노테이션 생략 가능
        /*
        if (constructors.length == 1) {
            for (Parameter parameter : constructors[0].getParameters()) {
                constructorDependencies.add(parameter.getType());
            }
            autowiredConstructor = constructors[0];
            return;
        }
         */

        for (Constructor<?> constructor : constructors) {
            if (!constructor.isAnnotationPresent(Autowired.class)) continue;

            for (Parameter parameter : constructor.getParameters()) {
                constructorDependencies.add(parameter.getType());
            }
            autowiredConstructor = constructor;
        }
    }

    @Override
    public String toString() {
        return beanType.getName();
    }
}
