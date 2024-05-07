package com.milktea.myspring.boot.web.utils.converters;

public interface ConversionService {
    Object convert(Object source, Class<?> targetType);
}
