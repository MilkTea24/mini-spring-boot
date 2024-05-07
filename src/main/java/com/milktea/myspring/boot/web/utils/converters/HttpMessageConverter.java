package com.milktea.myspring.boot.web.utils.converters;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface HttpMessageConverter {
    boolean canRead(Class<?> clazz, ContentType contentType);

    boolean canWrite(Class<?> clazz, ContentType contentType);

    List<ContentType> getSupportedMediaTypes();

    default List<ContentType> getSupportedMediaTypes(Class<?> clazz) {
        return (canRead(clazz, null) || canWrite(clazz, null) ?
                getSupportedMediaTypes() : Collections.emptyList());
    }

    //문자열 -> 객체
    Object read(Class<?> clazz, String inputMessage) throws IOException/*, HttpMessageNotReadableException*/;

    //객체 -> 문자열
    String write(Object t, ContentType contentType) throws IOException/*, HttpMessageNotWritableException*/;
}
