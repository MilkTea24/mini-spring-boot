package com.milktea.myspring.boot.web.ioc;

import com.milktea.myspring.boot.web.ioc.classes.ClassList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SingletonBeanFactoryTest {
    @BeforeEach
    void setup() {
    }

    @DisplayName("필드 주입 빈 생성 성공 테스트")
    @Test
    void singleton_bean_factory_success_test() {
        //given
        BeanDefinitionRegistry beanDefinitionRegistry = new BeanDefinitionRegistry();
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        beanDefinitions.add(new BeanDefinition(ClassList.A.class));
        beanDefinitions.add(new BeanDefinition(ClassList.B.class));
        beanDefinitions.add(new BeanDefinition(ClassList.C.class));
        beanDefinitionRegistry.addAll(beanDefinitions);

        BeanDefinitionScanner scanner = new MockBeanDefinitionScanner(beanDefinitionRegistry);
        SingletonBeanFactory singletonBeanFactory = new SingletonBeanFactory(scanner);

        //when
        singletonBeanFactory.createBeans();

        //then
        SingletonBeanRegistry instanceRegistry = singletonBeanFactory.getBeanRegistry();
        Assertions.assertNotNull(instanceRegistry.getSingleton(ClassList.A.class));
        Assertions.assertNotNull(instanceRegistry.getSingleton(ClassList.B.class));
        Assertions.assertNotNull(instanceRegistry.getSingleton(ClassList.C.class));
    }

    @DisplayName("생성자 주입 빈 생성 성공 테스트")
    @Test
    void singleton_bean_factory_constructor_success_test() {
        //given
        BeanDefinitionRegistry beanDefinitionRegistry = new BeanDefinitionRegistry();
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        beanDefinitions.add(new BeanDefinition(ClassList.D.class));
        beanDefinitions.add(new BeanDefinition(ClassList.E.class));
        beanDefinitions.add(new BeanDefinition(ClassList.F.class));
        beanDefinitions.add(new BeanDefinition(ClassList.G.class));
        beanDefinitionRegistry.addAll(beanDefinitions);

        BeanDefinitionScanner scanner = new MockBeanDefinitionScanner(beanDefinitionRegistry);
        SingletonBeanFactory singletonBeanFactory = new SingletonBeanFactory(scanner);

        //when
        singletonBeanFactory.createBeans();

        //then
        SingletonBeanRegistry instanceRegistry = singletonBeanFactory.getBeanRegistry();
        Assertions.assertNotNull(instanceRegistry.getSingleton(ClassList.D.class));
        Assertions.assertNotNull(instanceRegistry.getSingleton(ClassList.E.class));
        Assertions.assertNotNull(instanceRegistry.getSingleton(ClassList.F.class));
        Assertions.assertNotNull(instanceRegistry.getSingleton(ClassList.G.class));
    }

    @DisplayName("생성자 주입 빈 주입 성공 테스트")
    @Test
    void singleton_bean_factory_constructor_di_success_test() {
        //given
        BeanDefinitionRegistry beanDefinitionRegistry = new BeanDefinitionRegistry();
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        beanDefinitions.add(new BeanDefinition(ClassList.D.class));
        beanDefinitions.add(new BeanDefinition(ClassList.E.class));
        beanDefinitions.add(new BeanDefinition(ClassList.F.class));
        beanDefinitions.add(new BeanDefinition(ClassList.G.class));
        beanDefinitionRegistry.addAll(beanDefinitions);

        BeanDefinitionScanner scanner = new MockBeanDefinitionScanner(beanDefinitionRegistry);
        SingletonBeanFactory singletonBeanFactory = new SingletonBeanFactory(scanner);

        //when
        singletonBeanFactory.createBeans();

        //then
        SingletonBeanRegistry instanceRegistry = singletonBeanFactory.getBeanRegistry();
        ClassList.D instanceD = (ClassList.D) instanceRegistry.getSingleton(ClassList.D.class);
        Assertions.assertNotNull(instanceD.e);
        Assertions.assertNotNull(instanceD.f);
        ClassList.E instanceE = instanceD.e;
        Assertions.assertNotNull(instanceE.g);
    }

    @DisplayName("생성자 주입 빈 순환 참조시 실패 테스트")
    @Test
    void singleton_bean_factory_constructor_di_fail_test() {
        //given
        BeanDefinitionRegistry beanDefinitionRegistry = new BeanDefinitionRegistry();
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        beanDefinitions.add(new BeanDefinition(ClassList.H.class));
        beanDefinitions.add(new BeanDefinition(ClassList.I.class));
        beanDefinitionRegistry.addAll(beanDefinitions);

        BeanDefinitionScanner scanner = new MockBeanDefinitionScanner(beanDefinitionRegistry);
        SingletonBeanFactory singletonBeanFactory = new SingletonBeanFactory(scanner);

        //when, then
        Assertions.assertThrows(RuntimeException.class, singletonBeanFactory::createBeans);
    }

    class MockBeanDefinitionScanner implements BeanDefinitionScanner {
        private BeanDefinitionRegistry beanDefinitionRegistry;
        public MockBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
            this.beanDefinitionRegistry = beanDefinitionRegistry;
        }

        @Override
        public void scan(String baseString) {
        }

        @Override
        public BeanDefinitionRegistry getBeanDefinitionRegistry() {
            return beanDefinitionRegistry;
        }
    }
}
