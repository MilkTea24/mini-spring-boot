package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.servlet.test.TestPathVariableController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

public class PathVariableMethodArgumentResolverTest {
    private HandlerMethodArgumentResolver resolver;
    @BeforeEach
    void setup() {
        resolver = new PathVariableMethodArgumentResolver();
    }

    @DisplayName("PathVariable 성공 테스트")
    @Test
    void default_success_test() throws Exception {
        //given
        HttpServletRequest request = new MockHttpServletRequest("GET", "/user/1/order/2", "", null);

        Parameter[] parameters = TestPathVariableController.class.getMethod("defaultHandler", Integer.class, Long.class).getParameters();

        //when
        Object firstResult = resolver.resolveArgument(parameters[0], request);
        Object secondResult = resolver.resolveArgument(parameters[1], request);

        //then
        Assertions.assertTrue(firstResult instanceof Integer);
        Assertions.assertEquals(1, (Integer)firstResult);
        Assertions.assertTrue(secondResult instanceof Long);
        Assertions.assertEquals(2L, (Long)secondResult);
    }

    @DisplayName("PathVariable value 있을 때 성공 테스트")
    @Test
    void default_value_success_test() throws Exception {
        //given
        HttpServletRequest request = new MockHttpServletRequest("GET", "/user/1/order/2", "", null);

        Parameter[] parameters = TestPathVariableController.class.getMethod("valueHandler", Integer.class, Long.class).getParameters();

        //when
        Object firstResult = resolver.resolveArgument(parameters[0], request);
        Object secondResult = resolver.resolveArgument(parameters[1], request);

        //then
        Assertions.assertTrue(firstResult instanceof Integer);
        Assertions.assertEquals(1, (Integer)firstResult);
        Assertions.assertTrue(secondResult instanceof Long);
        Assertions.assertEquals(2L, (Long)secondResult);
    }

    @DisplayName("핸들러의 파라미터와 일치하는 PathVariable 이름 없을 때 실패 테스트")
    @Test
    void default_invalid_parameter_name_fail_test() throws Exception {
        //given
        HttpServletRequest request = new MockHttpServletRequest("GET", "/user/1/order/2", "", null);

        Parameter[] parameters = TestPathVariableController.class.getMethod("invalidArgumentHandler", Integer.class, Long.class).getParameters();

        //when
        //then
        Assertions.assertThrows(RuntimeException.class, () -> resolver.resolveArgument(parameters[0], request));
    }
}
