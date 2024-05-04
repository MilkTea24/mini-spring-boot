package com.milktea.myspring.boot.web;

import com.milktea.myspring.annotations.Component;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassPathBeanDefinitionScanner implements BeanDefinitionScanner {
    //전체 프로젝트의 빈 정보를 담고 있음 빈을 생성하기 전 필요
    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner() {
        this.registry = new BeanDefinitionRegistry();
    }

    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return registry;
    }

    //스캔하여 현재 프로젝트에 존재하는 빈의 정보를 저장
    public void scan(String basePackage) {
        String path = basePackage.replace('.', '/');

        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                registry.addAll(findClasses(resource, basePackage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<BeanDefinition> findClasses(URL resource, String basePackage) {
        List<BeanDefinition> classes = new ArrayList<>();

        try {
            String decodedPath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8.toString());
            File directory = new File(decodedPath);
            if (!directory.exists()) return classes;
            File[] files = directory.listFiles();

            if (files == null) return classes;

            for (File file : files) {
                //하위 파일이 디렉토리인경우
                if (file.isDirectory()) {
                    classes.addAll(findClasses(new URL(file.toURI().toString()), basePackage + "." + file.getName()));
                }
                //하위 파일이 클래스 파일인경우
                else if (file.getName().endsWith(".class")) {
                    String className = basePackage + '.' + file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(className);
                    Annotation[] annotations = clazz.getAnnotations();

                    //메타 어노테이션으로 Component를 가질 경우 BeanDefinition 생성
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType().getAnnotation(Component.class) != null) {
                            classes.add(new BeanDefinition(clazz));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }



}