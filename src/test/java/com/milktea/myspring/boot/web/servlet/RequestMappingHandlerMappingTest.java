package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.ioc.AnnotationConfigApplicationContext;
import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class RequestMappingHandlerMappingTest {
    private RequestMappingHandlerMapping handlerMapping;

    @BeforeEach
    void setup() {
        ApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh("com.milktea.myspring.boot.web.servlet");
        handlerMapping = new RequestMappingHandlerMapping(new ControllerRegistry(context));
    }

    @DisplayName("RequestMapping getHandler 성공 테스트")
    @Test
    void request_mapping_get_handler_success_test() throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/user/1/order/2", "hello");

        //when
        Method resultHandler = handlerMapping.getHandler(request);

        //then
        Assertions.assertEquals("successMethod", resultHandler.getName());
    }

    @DisplayName("RequestMapping getHandler 존재하지 않는 요청경로 실패 테스트")
    @Test
    void request_mapping_get_handler_fail_test() throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/user/order", "hello");

        //when, then
        Assertions.assertThrows(RuntimeException.class, () -> handlerMapping.getHandler(request));
    }

    @DisplayName("GetMapping getHandler 성공 테스트")
    @Test
    void get_mapping_get_handler_success_test() throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/user/1/details", "hello");

        //when
        Method resultHandler = handlerMapping.getHandler(request);

        //then
        Assertions.assertEquals("successGetMethod", resultHandler.getName());
    }

    @DisplayName("PostMapping getHandler 성공 테스트")
    @Test
    void post_mapping_get_handler_success_test() throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user/1/details/order", "hello");

        //when
        Method resultHandler = handlerMapping.getHandler(request);

        //then
        Assertions.assertEquals("successPostMethod", resultHandler.getName());
    }

    @DisplayName("PostMapping getHandler Method 다를 경우 실패 테스트")
    @Test
    void post_mapping_get_handler_invalid_method_fail_test() throws Exception {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/user/1/details/order", "hello");

        //when, then
        Assertions.assertThrows(RuntimeException.class, () -> handlerMapping.getHandler(request));
    }
}
