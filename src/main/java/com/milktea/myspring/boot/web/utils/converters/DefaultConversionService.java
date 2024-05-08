package com.milktea.myspring.boot.web.utils.converters;

import org.hibernate.boot.model.internal.XMLContext;

import java.util.ArrayList;
import java.util.List;

public class DefaultConversionService implements ConversionService {
    List<Converter<?, ?>> converters = new ArrayList<>();

    private static DefaultConversionService instance;

    private DefaultConversionService() {}

    public static DefaultConversionService getInstance() {
        if (instance == null) {
            instance = new DefaultConversionService();
            instance.addDefaultConverters();
        }

        return instance;
    }

    public void addDefaultConverters() {
        converters.add(new StringToLongConverter());
        converters.add(new StringToIntegerConverter());
        converters.add(new StringToBooleanConverter());
    }

    @Override
    public <T> T convert(Object source, Class<?> targetType) {
        if (source == null) return null;

        for (Converter<?, ?> converter : converters) {
            if (converter.matches(source.getClass(), targetType)) {
                Converter<Object, Object> objectConverter = (Converter<Object, Object>) converter;
                return (T)objectConverter.convert(source);
            }
        }
        return null;
    }


}
