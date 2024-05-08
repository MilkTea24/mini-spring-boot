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
    ApplicationContext context = new AnnotationConfigApplicationContext();

    DispatcherServlet ds;
    @BeforeEach
    @Test
    void setup() {
        context.refresh("com.milktea.myspring.boot.web.servlet.test");
        ds = new DispatcherServlet(context);
    }

    @DisplayName("RequestParam, PathVariable 성공 테스트")
    @Test
    void handle_success_test() throws Exception {
        //given
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
