package edu.pnu.myspring.core;

import edu.pnu.myspring.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class MyApplicationContext {

    private Map<Class<?>, Object> beanRegistry = new HashMap<>();

    private List<Object> beansToAutowire = new ArrayList<>();

    private Map<Object, Method> postConstructMethodRegistry = new HashMap<>();

    private Map<Object, Method> preDestroyMethodRegistry = new HashMap<>();



    public MyApplicationContext(String basePackage) {
        //System.out.println("MyApplicationContext 생성");

        scanAndRegisterBeans(basePackage);

        processAutowiring();

    }

    public <T> void registerBean(Class<? extends T> beanClass) {
        if (beanRegistry.containsKey(beanClass)) return;

        try {
            Object instance = beanClass.getDeclaredConstructor().newInstance();
            checkMethodAnnotation(beanClass, instance);
            checkAutowired(beanClass, instance);
            beanRegistry.put(beanClass, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> void checkAutowired(Class<? extends T> beanClass, Object instance) {
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(MyAutowired.class)) {
                beansToAutowire.add(instance);
            }
        }
    }

    public <T> T getBean(Class<T> type) {
        if (!beanRegistry.containsKey(type))
            throw new RuntimeException(type.getName() + "클래스는 존재하지 않습니다.");
        // implement your code

        return (T)beanRegistry.get(type);
    }

    public Map<Class<?>, Object> getBeans(Class<? extends Annotation> annotationClass) {
        Map<Class<?>, Object> annotationBeans = new HashMap<>();

        for (Map.Entry<Class<?>, Object> bean : beanRegistry.entrySet()) {
            Class<?> beanClass = bean.getKey();
            Object instance = bean.getValue();

            if (beanClass.isAnnotationPresent(annotationClass)) annotationBeans.put(beanClass, instance);
        }

        return annotationBeans;
    }

    public void close() {
        executePreDestroy();
        // implement your code

    }

    private void scanAndRegisterBeans(String basePackage) {
        String path = basePackage.replace('.', '/');
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                List<Class<?>> classes = findClasses(resource, basePackage);
                for (Class<?> clazz : classes) {
                    registerBean(clazz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executePostConstruct();
    }

    private void executePostConstruct() {
        for(Map.Entry<Object,Method> entry : postConstructMethodRegistry.entrySet()) {
            Object instance = entry.getKey();
            Method method = entry.getValue();

            try {
                method.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Class<?>> findClasses(URL resource, String basePackage) {
        List<Class<?>> classes = new ArrayList<>();
        List<Class<? extends Annotation>> annotations = new ArrayList<>();
        annotations.add(MyRepository.class);    annotations.add(MyRestController.class);
        annotations.add(MyService.class);



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
                    for (Class<? extends Annotation> annotation : annotations) {
                        if (clazz.isAnnotationPresent(annotation)) {
                            classes.add(clazz);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private void executePreDestroy() {
        for(Map.Entry<Object,Method> entry : preDestroyMethodRegistry.entrySet()) {
            Object instance = entry.getKey();
            Method method = entry.getValue();

            try {
                method.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void processAutowiring() {
        try {
            for (Object instance : beansToAutowire) {
                Field[] fields = instance.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(MyAutowired.class)) continue;
                    Object dependInstance = beanRegistry.get(field.getType());
                    field.setAccessible(true);
                    field.set(instance, dependInstance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkMethodAnnotation(Class<?> clazz, Object instance) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(PostConstruct.class))
                postConstructMethodRegistry.put(instance, method);
            else if (method.isAnnotationPresent(PreDestroy.class))
                preDestroyMethodRegistry.put(instance, method);
        }
    }
}