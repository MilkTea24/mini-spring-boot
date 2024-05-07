package com.milktea.myspring.boot.web.servlet;

import org.apache.tomcat.util.http.parser.MediaType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface HttpMessageConverter<T> {
    boolean canRead(Class<?> clazz, MediaType mediaType);

    boolean canWrite(Class<?> clazz, MediaType mediaType);

    List<MediaType> getSupportedMediaTypes();

    default List<MediaType> getSupportedMediaTypes(Class<?> clazz) {
        return (canRead(clazz, null) || canWrite(clazz, null) ?
                getSupportedMediaTypes() : Collections.emptyList());
    }

    T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException/*, HttpMessageNotReadableException*/;

    void write(T t, MediaType contentType, HttpOutputMessage outputMessage) throws IOException/*, HttpMessageNotWritableException*/;
}
