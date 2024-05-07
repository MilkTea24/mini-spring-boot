package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.annotations.RequestBody;
import com.milktea.myspring.boot.web.utils.converters.ContentType;
import com.milktea.myspring.boot.web.utils.converters.HttpMessageConverter;
import com.milktea.myspring.boot.web.utils.converters.MappingJackson2HttpMessageConverter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;

public class RequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver {
    HttpMessageConverter<?> httpMessageConverter;
    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolveArgument(Parameter parameter, HttpServletRequest request) {
        String requestBody = getBody(request);
        Object result = null;
        try {
            if (httpMessageConverter.canRead(parameter.getType(), ContentType.fromString(request.getContentType())))
                result = httpMessageConverter.read(parameter.getType(), requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getBody(HttpServletRequest request) {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = request.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
            if (inputStream != null) {
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        body = stringBuilder.toString();

        return body;
    }
 }
