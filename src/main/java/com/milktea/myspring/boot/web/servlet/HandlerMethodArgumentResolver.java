package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(Parameter parameter);

    Object resolveArgument(Parameter parameter, HttpServletRequest request);
}
