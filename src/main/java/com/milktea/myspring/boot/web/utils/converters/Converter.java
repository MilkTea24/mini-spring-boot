package com.milktea.myspring.boot.web.utils.converters;

public interface Converter<S, T> {
    T convert(S source);

    boolean matches(Class<?> source, Class<?> target);
}
