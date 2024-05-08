package com.milktea.myspring.boot.web.utils.converters;

public interface ConversionService {
    <T> T convert(Object source, Class<?> targetType);
}
