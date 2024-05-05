package com.milktea.myspring.boot.web;

import com.milktea.myspring.boot.web.classes.X;
import com.milktea.myspring.boot.web.classes.Z;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClassPathBeanDefinitionScannerTest {
    private ClassPathBeanDefinitionScanner scanner;

    @BeforeEach
    void setup() {
        scanner = new ClassPathBeanDefinitionScanner();
    }

    @Test
    void classpath_bean_definition_scanner_success_test() {
        //given
        String currentPath = "com.milktea.myspring.boot.web";

        //when
        scanner.scan(currentPath);

        //then
        BeanDefinitionRegistry registry = scanner.getBeanDefinitionRegistry();
        System.out.print(registry.getBeanDefinitions());
        Assertions.assertNotNull(registry.getBeanDefinition(ClassList.A.class));
        Assertions.assertNotNull(registry.getBeanDefinition(X.class));
        Assertions.assertNull(registry.getBeanDefinition(Z.class));
    }
}
