package com.milktea.myspring.boot.web.utils.converters;

public class StringToBooleanConverter implements Converter<String, Boolean> {
    @Override
    public Boolean convert(String source) {
        return Boolean.parseBoolean(source);
    }

    @Override
    public boolean matches(Class<?> source, Class<?> target) {
        return source == String.class && (target == Boolean.class || target == boolean.class);
    }
}
