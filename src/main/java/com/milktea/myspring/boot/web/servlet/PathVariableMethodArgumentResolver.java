package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.annotations.PathVariable;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public class PathVariableMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(Parameter parameter, HttpServletRequest request, WebDataBinderFactory binderFactory) {
        return null;
    }
}
