package com.milktea.myspring.boot.web.utils.converters;

public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        return Integer.valueOf(source);
    }

    @Override
    public boolean matches(Class<?> source, Class<?> target) {
        return source == String.class && (target == Integer.class || target == int.class);
    }
}
