package com.milktea.myspring.boot.web.utils.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultConversionServiceTest {
    ConversionService conversionService = DefaultConversionService.getInstance();
    @DisplayName("String -> Integer 변환 성공 테스트")
    @Test
    void convert_string_to_integer_success_test() {
        //given
        String source = "2";

        //when
        Object result = conversionService.convert(source, Integer.class);

        //then
        Assertions.assertTrue(result instanceof Integer);
        Assertions.assertEquals(2, (Integer)result);
    }

    @DisplayName("String -> Long 변환 성공 테스트")
    @Test
    void convert_string_to_long_success_test() {
        //given
        String source = "2";

        //when
        Object result = conversionService.convert(source, Long.class);

        //then
        Assertions.assertTrue(result instanceof Long);
        Assertions.assertEquals(2L, (Long)result);
    }
}
