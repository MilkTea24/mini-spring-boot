package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(Parameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(Parameter parameter, HttpServletRequest request, WebDataBinderFactory binderFactory) {
        return null;
    }
}
