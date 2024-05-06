package com.milktea.myspring.boot.web.ioc;

import com.milktea.myspring.boot.web.ioc.classes.ClassList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAnnotationBeanPostProcessorTest {
    MockBeanFactory mockBeanFactory;
    @BeforeEach
    void setup() {
        Map<Class<?>, Object> instances = new HashMap<>();
        instances.put(ClassList.F.class, new ClassList.F());
        instances.put(ClassList.G.class, new ClassList.G());
        BeanRegistry beanRegistry = new DefaultSingletonBeanRegistry(instances);

        List<BeanDefinition> beanDefinitionList = new ArrayList<>();
        beanDefinitionList.add(new BeanDefinition(ClassList.F.class));
        beanDefinitionList.add(new BeanDefinition(ClassList.G.class));
        BeanDefinitionRegistry beanDefinitionRegistry = new BeanDefinitionRegistry();
        beanDefinitionRegistry.addAll(beanDefinitionList);

        mockBeanFactory = new MockBeanFactory(beanRegistry, beanDefinitionRegistry);
    }

    @DisplayName("PostConstruct 메서드 성공 테스트")
    @Test
    void post_construct_success_test() {
        //given
        CommonAnnotationBeanPostProcessor postProcessor = new CommonAnnotationBeanPostProcessor(mockBeanFactory);

        //when
        postProcessor.postConstruct();

        //then
        ClassList.F f = (ClassList.F) mockBeanFactory.getBeanRegistry().getSingleton(ClassList.F.class);
        Assertions.assertEquals(2, f.number);
        ClassList.G g = (ClassList.G) mockBeanFactory.getBeanRegistry().getSingleton(ClassList.G.class);
        Assertions.assertEquals(0, g.number);
    }

    @DisplayName("PreDestroy 메서드 성공 테스트")
    @Test
    void pre_destroy_success_test() {
        //given
        CommonAnnotationBeanPostProcessor postProcessor = new CommonAnnotationBeanPostProcessor(mockBeanFactory);

        //when
        postProcessor.preDestroy();

        //then
        ClassList.F f = (ClassList.F) mockBeanFactory.getBeanRegistry().getSingleton(ClassList.F.class);
        Assertions.assertEquals(0, f.number);
        ClassList.G g = (ClassList.G) mockBeanFactory.getBeanRegistry().getSingleton(ClassList.G.class);
        Assertions.assertEquals(2, g.number);
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
