package com.milktea.myspring.boot.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutowiredAnnotationBeanPostProcessorTest {
    BeanRegistry beanRegistry;
    BeanDefinitionRegistry beanDefinitionRegistry;

    MockBeanFactory mockBeanFactory;
    @BeforeEach
    void setup() {
        Map<Class<?>, Object> instances = new HashMap<>();
        instances.put(ClassList.A.class, new ClassList.A());
        instances.put(ClassList.B.class, new ClassList.B());
        instances.put(ClassList.C.class, new ClassList.C());
        beanRegistry = new DefaultSingletonBeanRegistry(instances);

        List<BeanDefinition> beanDefinitionList = new ArrayList<>();
        beanDefinitionList.add(new BeanDefinition(ClassList.A.class));
        beanDefinitionList.add(new BeanDefinition(ClassList.B.class));
        beanDefinitionList.add(new BeanDefinition(ClassList.C.class));
        beanDefinitionRegistry = new BeanDefinitionRegistry();
        beanDefinitionRegistry.addAll(beanDefinitionList);

        mockBeanFactory = new MockBeanFactory(beanRegistry, beanDefinitionRegistry);
    }

    @DisplayName("빈 필드 주입 성공 테스트")
    @Test
    void autowired_annotation_bean_di_success_test() {
        //given
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor(mockBeanFactory);

        //when
        autowiredAnnotationBeanPostProcessor.setAutowired();

        //then
        ClassList.A a = (ClassList.A)mockBeanFactory.getBeanRegistry().getSingleton(ClassList.A.class);
        Assertions.assertNotNull(a.b);
        Assertions.assertEquals(ClassList.B.class, a.b.getClass());
        Assertions.assertNotNull(a.b.c);
    }

    class MockBeanFactory implements BeanFactory {
        BeanRegistry beanRegistry;
        BeanDefinitionRegistry beanDefinitionRegistry;

        private MockBeanFactory(BeanRegistry beanRegistry, BeanDefinitionRegistry beanDefinitionRegistry) {
            this.beanRegistry = beanRegistry;
            this.beanDefinitionRegistry = beanDefinitionRegistry;
        }

        @Override
        public void createBeans() {
        }

        @Override
        public BeanRegistry getBeanRegistry() {
            return beanRegistry;
        }

        @Override
        public BeanDefinitionRegistry getBeanDefinitionRegistry() {
            return beanDefinitionRegistry;
        }
    }
}
