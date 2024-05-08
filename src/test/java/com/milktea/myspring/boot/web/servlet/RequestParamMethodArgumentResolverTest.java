package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.servlet.test.TestRequestParamController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class RequestParamMethodArgumentResolverTest {
    private HandlerMethodArgumentResolver resolver;
    private Map<String, String[]> requestParameters;
    @BeforeEach
    void setup() {
        resolver = new RequestParamMethodArgumentResolver();
        requestParameters = new HashMap<>();
        requestParameters.put("userId", new String[]{"1"});
        requestParameters.put("orderId", new String[]{"2"});
    }

    @DisplayName("RequestParam 어노테이션 없을 때 성공 테스트")
    @Test
    void default_success_test() throws Exception {
        //given
        HttpServletRequest request = new MockHttpServletRequest("GET", "/hello1?userId=1&orderId=2", "", requestParameters);

        Parameter[] parameters = TestRequestParamController.class.getMethod("defaultHandler", Integer.class, Long.class).getParameters();

        //when
        Object firstResult = resolver.resolveArgument(parameters[0], request);
        Object secondResult = resolver.resolveArgument(parameters[1], request);

        //then
        Assertions.assertTrue(firstResult instanceof Integer);
        Assertions.assertEquals(1, (Integer)firstResult);
        Assertions.assertTrue(secondResult instanceof Long);
        Assertions.assertEquals(2L, (Long)secondResult);
    }

    @DisplayName("RequestParam 어노테이션 있을 때 성공 테스트")
    @Test
    void request_param_default_success_test() throws Exception {
        //given
        HttpServletRequest request = new MockHttpServletRequest("GET", "/hello2?userId=1&orderId=2", "", requestParameters);

        Parameter[] parameters = TestRequestParamController.class.getMethod("requestParamDefaultHandler", Integer.class, Long.class).getParameters();

        //when
        Object firstResult = resolver.resolveArgument(parameters[0], request);
        Object secondResult = resolver.resolveArgument(parameters[1], request);

        //then
        Assertions.assertTrue(firstResult instanceof Integer);
        Assertions.assertEquals(1, (Integer)firstResult);
        Assertions.assertTrue(secondResult instanceof Long);
        Assertions.assertEquals(2L, (Long)secondResult);
    }

    @DisplayName("RequestParam 어노테이션 value 값 있을 때 성공 테스트")
    @Test
    void request_param_value_success_test() throws Exception {
        //given
        HttpServletRequest request = new MockHttpServletRequest("GET", "/hello3?userId=1&orderId=2", "", requestParameters);

        Parameter[] parameters = TestRequestParamController.class.getMethod("requestParamValueHandler", Integer.class, Long.class).getParameters();

        //when
        Object firstResult = resolver.resolveArgument(parameters[0], request);
        Object secondResult = resolver.resolveArgument(parameters[1], request);

        //then
        Assertions.assertTrue(firstResult instanceof Integer);
        Assertions.assertEquals(1, (Integer)firstResult);
        Assertions.assertTrue(secondResult instanceof Long);
        Assertions.assertEquals(2L, (Long)secondResult);
    }

    @DisplayName("RequestParam 어노테이션 required false일 때 성공 테스트")
    @Test
    void request_param_required_false_success_test() throws Exception {
        //given
        requestParameters.remove("orderId");
        HttpServletRequest request = new MockHttpServletRequest("GET", "/hello4?userId=1", "", requestParameters);

        Parameter[] parameters = TestRequestParamController.class.getMethod("requestParamRequiredFalseHandler", Integer.class, Long.class).getParameters();

        //when
        Object firstResult = resolver.resolveArgument(parameters[0], request);
        Object secondResult = resolver.resolveArgument(parameters[1], request);

        //then
        Assertions.assertTrue(firstResult instanceof Integer);
        Assertions.assertEquals(1, (Integer)firstResult);
        Assertions.assertNull(secondResult);
    }

    @DisplayName("RequestParam 어노테이션 required true일 때 실패 테스트")
    @Test
    void request_param_required_true_fail_test() throws Exception {
        //given
        requestParameters.remove("orderId");
        HttpServletRequest request = new MockHttpServletRequest("GET", "/hello5?userId=1", "", requestParameters);

        Parameter[] parameters = TestRequestParamController.class.getMethod("requestParamRequiredTrueHandler", Integer.class, Long.class).getParameters();

        //when
        //then
        Assertions.assertThrows(RuntimeException.class, () -> resolver.resolveArgument(parameters[1], request));
    }

    @DisplayName("RequestParam 어노테이션 default value 있을 때 성공 테스트")
    @Test
    void request_param_default_value_success_test() throws Exception {
        //given
        requestParameters.remove("orderId");
        HttpServletRequest request = new MockHttpServletRequest("GET", "/hello5?userId=1", "", requestParameters);

        Parameter[] parameters = TestRequestParamController.class.getMethod("requestParamDefaultValueHandler", Integer.class, Long.class).getParameters();

        //when
        Object firstResult = resolver.resolveArgument(parameters[0], request);
        Object secondResult = resolver.resolveArgument(parameters[1], request);

        //then
        Assertions.assertTrue(firstResult instanceof Integer);
        Assertions.assertEquals(1, (Integer)firstResult);
        Assertions.assertTrue(secondResult instanceof Long);
        Assertions.assertEquals(2L, (Long)secondResult);
    }
}
