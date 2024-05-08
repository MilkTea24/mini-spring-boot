package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.ioc.AnnotationConfigApplicationContext;
import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class DispatcherServletTest {
    private ApplicationContext context = new AnnotationConfigApplicationContext();

    private DispatcherServlet ds;

    @DisplayName("RequestBody, PathVariable 성공 테스트")
    @Test
    void handle_post_success_test() throws Exception {
        //given
        context.refresh("com.milktea.myspring.boot.web.servlet.test");
        ds = new DispatcherServlet(context);

        String requestBody = """
                {
                    "id": 1,
                    "username": "user",
                    "password": "user1234"
                }
                """;

        String responseBody = """
                {"userId":1,"orderId":2}""";

        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user/1", requestBody, null);
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        ds.doPost(request, response);

        //then
        Assertions.assertEquals(responseBody, response.stringWriter.toString());
    }

    @DisplayName("RequestParam, PathVariable 성공 테스트")
    @Test
    void handle_get_success_test() throws Exception {
        //given
        context.refresh("com.milktea.myspring.boot.web.servlet.test");
        ds = new DispatcherServlet(context);

        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("details", new String[]{"true"});
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/user/1/order/2?details=true", "", parameters);
        MockHttpServletResponse response = new MockHttpServletResponse();

        String responseBody = """
                {"userId":1,"orderId":2}""";

        //when
        ds.doGet(request, response);

        //then
        Assertions.assertEquals(responseBody, response.stringWriter.toString());
    }

}
