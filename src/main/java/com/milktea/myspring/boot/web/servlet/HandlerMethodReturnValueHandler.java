package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletResponse;

public interface HandlerMethodReturnValueHandler {
    boolean supportsReturnType(Class<?> returnType);
    void handleReturnValue(Object returnValue, Class<?> returnType, /*ModelAndViewContainer* mavContainer, */ HttpServletResponse response);
}
