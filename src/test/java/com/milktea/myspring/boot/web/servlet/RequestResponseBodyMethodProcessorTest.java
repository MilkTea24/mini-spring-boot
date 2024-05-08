package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.servlet.test.TestRequestBodyController;
import com.milktea.myspring.boot.web.servlet.test.UserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

public class RequestResponseBodyMethodProcessorTest {
    private RequestResponseBodyMethodProcessor processor;

    @BeforeEach
    void setup() {
       processor = new RequestResponseBodyMethodProcessor();
    }

    @DisplayName("RequestBody 성공 테스트")
    @Test
    void default_success_test() throws Exception {
        //given
        String requestBody = """
                {
                    "id" : 1,
                    "username" : "user1",
                    "password" : "user1234"
                }
                """;
        HttpServletRequest request = new MockHttpServletRequest("POST", "/register1", requestBody, null);
        Parameter parameter = TestRequestBodyController.class.getMethod("defaultHandler", UserRequestDto.class).getParameters()[0];

        //when
        Object result = processor.resolveArgument(parameter, request);

        //then
        Assertions.assertTrue(result instanceof UserRequestDto);
        UserRequestDto dto = (UserRequestDto) result;
        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("user1", dto.getUsername());
    }
}
