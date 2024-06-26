package com.milktea.myspring.boot.web.ioc;

import java.lang.annotation.Annotation;
import java.util.Map;

//genericapplicationcontext
public class AnnotationConfigApplicationContext implements ApplicationContext {
    //빈들을 스캔하여 등록
    private BeanDefinitionScanner scanner;

    private BeanFactory beanFactory;

    private SingletonBeanRegistry beanRegistry;

    private AutowiredAnnotationBeanPostProcessor autowiredPostProcessor;

    private CommonAnnotationBeanPostProcessor commonPostProcessor;


    @Override
    public void refresh(String basePackage) {
        //1. 빈 정의
        scanner = new ClassPathBeanDefinitionScanner();
        scanner.scan(basePackage);

        //2. 빈 생성
        beanFactory = new SingletonBeanFactory(scanner);
        beanFactory.createBeans();
        beanRegistry = beanFactory.getBeanRegistry();

        //3. 의존관계 주입
        autowiredPostProcessor = new AutowiredAnnotationBeanPostProcessor(beanFactory);
        autowiredPostProcessor.setAutowired();

        //4. 초기화 콜백 PostConstruct
        commonPostProcessor = new CommonAnnotationBeanPostProcessor(beanFactory);
        commonPostProcessor.postConstruct();
    }

    @Override
    public void close() {
        //5. 종료 전
        commonPostProcessor.preDestroy();

        scanner = null;
        beanFactory = null;
        autowiredPostProcessor = null;
        commonPostProcessor = null;
    }

    @Override
    public Map<Class<?>, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationClass) {
        return beanRegistry.getSingletonsWithAnnotation(annotationClass);
    }

    @Override
    public Object getBean(Class<?> clazz) {
        return beanRegistry.getSingleton(clazz);
    }
}