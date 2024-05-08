package com.milktea.myspring.boot.web.utils.converters;

public class StringToLongConverter implements Converter<String, Long> {
    @Override
    public Long convert(String source) {
        return Long.valueOf(source);
    }

    @Override
    public boolean matches(Class<?> source, Class<?> target) {
        return source == String.class && (target == Long.class || target == long.class);
    }
}
