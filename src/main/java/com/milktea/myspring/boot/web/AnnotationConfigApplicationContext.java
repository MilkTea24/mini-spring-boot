package com.milktea.myspring.boot.web;

//genericapplicationcontext
public class AnnotationConfigApplicationContext {
    //빈들을 스캔하여 등록
    private BeanDefinitionScanner scanner;

    private BeanFactory beanFactory;

    private AutowiredAnnotationBeanPostProcessor autowiredPostProcessor;


    public void refresh(String basePackage) {
        //1. 빈 정의
        scanner = new ClassPathBeanDefinitionScanner();
        scan(basePackage);

        //2. 빈 생성
        beanFactory = new SingletonBeanFactory(scanner);
        beanFactory.createBeans();

        //3. 의존관계 주입
        autowiredPostProcessor = new AutowiredAnnotationBeanPostProcessor(beanFactory);
        autowiredPostProcessor.setAutowired();

        //초기화 콜백 PostConstruct

        //
    }

    public void scan(String basePackage) {
        this.scanner.scan(basePackage);
    }


}