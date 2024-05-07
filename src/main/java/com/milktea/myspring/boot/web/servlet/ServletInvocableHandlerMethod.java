package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class ServletInvocableHandlerMethod {
    Method handler;

    List<HandlerMethodArgumentResolver> resolvers;
    public ServletInvocableHandlerMethod(Method handler, List<HandlerMethodArgumentResolver> resolvers) {
        this.handler = handler;
        this.resolvers = resolvers;
    }

    public void invokeAndHandle(HttpServletRequest request, HttpServletResponse response) {
        String returnValue = invokeForRequest(request);

        handleForResponse(response);
    }

    private String invokeForRequest(HttpServletRequest request) {
        Object[] args = getMethodArgumentValues(request);

        return doInvoke(args);
    }

    private Object[] getMethodArgumentValues(HttpServletRequest request) {
        Parameter[] parameters = handler.getParameters();
        Object[] results = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            for (HandlerMethodArgumentResolver resolver : resolvers) {
                if (!resolver.supportsParameter(parameters[i])) continue;

                results[i] = resolver.resolveArgument(parameters[i], request, binderFactory);
            }
        }
    }
}
