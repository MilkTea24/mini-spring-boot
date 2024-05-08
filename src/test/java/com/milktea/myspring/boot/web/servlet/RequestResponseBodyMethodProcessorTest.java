package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.servlet.test.TestRequestResponseBodyController;
import com.milktea.myspring.boot.web.servlet.test.UserRequestDto;
import com.milktea.myspring.boot.web.servlet.test.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    void default_request_body_success_test() throws Exception {
        //given
        String requestBody = """
                {
                    "id" : 1,
                    "username" : "user1",
                    "password" : "user1234"
                }
                """;
        HttpServletRequest request = new MockHttpServletRequest("POST", "/register1", requestBody, null);
        Parameter parameter = TestRequestResponseBodyController.class.getMethod("defaultHandler", UserRequestDto.class).getParameters()[0];

        //when
        Object result = processor.resolveArgument(parameter, request);

        //then
        Assertions.assertTrue(result instanceof UserRequestDto);
        UserRequestDto dto = (UserRequestDto) result;
        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("user1", dto.getUsername());
    }

    @DisplayName("ResponseBody 성공 테스트")
    @Test
    void default_response_body_success_test() throws Exception {
        //given
        String responseBody = """
                {"id":1,"username":"user1"}""";
        MockHttpServletResponse response = new MockHttpServletResponse();
        UserResponseDto dto = new UserResponseDto(1L, "user1");

        //when
        processor.handleReturnValue(dto, UserRequestDto.class, response);

        //then
        Assertions.assertEquals(responseBody, response.stringWriter.toString());
    }
}
