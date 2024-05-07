package com.milktea.myspring.boot.web.utils.converters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class MappingJackson2HttpMessageConverter implements HttpMessageConverter<Object> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean canRead(Class<?> clazz, ContentType contentType) {
        return contentType.equals(ContentType.JSON);
    }

    @Override
    public boolean canWrite(Class<?> clazz, ContentType contentType) {
         return contentType.equals(ContentType.JSON);
    }

    @Override
    public List<ContentType> getSupportedMediaTypes() {
        return List.of(ContentType.JSON);
    }

    @Override
    public List<ContentType> getSupportedMediaTypes(Class<?> clazz) {
        return List.of(ContentType.JSON);
    }

    @Override
    public Object read(Class<?> clazz, String inputMessage) throws IOException {
        return objectMapper.readValue(inputMessage, clazz);
    }

    @Override
    public String write(Object o, ContentType contentType) throws IOException {
        return objectMapper.writeValueAsString(o);
    }
}
