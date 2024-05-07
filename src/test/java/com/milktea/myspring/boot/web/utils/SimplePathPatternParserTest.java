package com.milktea.myspring.boot.web.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Map;

public class SimplePathPatternParserTest {
    private static final String pattern = "/user/{userId}/order/{orderId}";

    private SimplePathPatternParser parser;

    @BeforeEach
    void setup() {
        parser = new SimplePathPatternParser(pattern);
    }

    @DisplayName("PathVariable 매칭 성공 테스트")
    @Test
    void simple_path_pattern_parser_matching_success_test() throws Exception {
        //given
        Method method = TestController.class.getMethod("successMethod", int.class, long.class);
        String requestPath = "/user/1/order/2";

        //when
        boolean matchResult = parser.match(requestPath);

        //then
        Assertions.assertTrue(matchResult);
    }

    @DisplayName("PathVariable 매칭 실패 테스트")
    @Test
    void simple_path_pattern_parser_matching_fail_test() throws Exception {
        //given
        Method method = TestController.class.getMethod("successMethod", int.class, long.class);
        String requestPath = "/user/1/invalid/2";

        //when
        boolean matchResult = parser.match(requestPath);

        //then
        Assertions.assertFalse(matchResult);
    }

    @DisplayName("PathVariable 어노테이션의 value가 없을 때 파라미터 매핑 성공 테스트")
    @Test
    void simple_path_pattern_parser_null_value_mapping_success_test() throws Exception {
        //given
        Method method = TestController.class.getMethod("successMethod", int.class, long.class);
        String requestPath = "/user/1/order/2";

        //when
        Map<String, Object> result = parser.getPathVariables(method, requestPath);

        //then
        Assertions.assertEquals(1, result.get("userId"));
        Assertions.assertEquals(2L, result.get("orderId"));
    }

    @DisplayName("PathVariable 어노테이션의 value가 있을 때 파라미터 매핑 성공 테스트")
    @Test
    void simple_path_pattern_parser_non_null_value_mapping_success_test() throws Exception {
        //given
        Method method = TestController.class.getMethod("successValueMethod", int.class, long.class);
        String requestPath = "/user/1/order/2";

        //when
        Map<String, Object> result = parser.getPathVariables(method, requestPath);

        //then
        Assertions.assertEquals(1, result.get("userId"));
        Assertions.assertEquals(2L, result.get("orderId"));
    }

    @DisplayName("PathVariable과 매핑되는 적절한 파라미터를 찾을 수 없을 때 파라미터 매핑 실패 테스트")
    @Test
    void simple_path_pattern_parser_invalid_parameter_mapping_fail_test() throws Exception {
        //given
        Method method = TestController.class.getMethod("failMethod", int.class, long.class);
        String requestPath = "/user/1/order/2";

        //when, then
        Assertions.assertThrows(RuntimeException.class, () -> parser.getPathVariables(method, requestPath));
    }

    @DisplayName("잘못된 requestPath일 경우 파라미터 매핑 실패 테스트")
    @Test
    void simple_path_pattern_parser_invalid_path_mapping_fail_test() throws Exception {
        //given
        Method method = TestController.class.getMethod("successMethod", int.class, long.class);
        String requestPath = "/user/1/order";

        //when, then
        Assertions.assertThrows(RuntimeException.class, () -> parser.getPathVariables(method, requestPath));
    }
}
