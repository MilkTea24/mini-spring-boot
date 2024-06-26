package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private final ApplicationContext context;

    private List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

    public RequestMappingHandlerAdapter(ApplicationContext context) {
        this.context = context;
        getDefaultArgumentResolvers();
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Method handler) {
        invokeHandlerMethod(request, response, handler);
    }

    private void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Method handler) {
        ServletInvocableHandlerMethod invocableMethod = new ServletInvocableHandlerMethod(handler, resolvers);

        invocableMethod.setContext(context);

        invocableMethod.invokeAndHandle(request, response);
    }

    private void getDefaultArgumentResolvers() {
        resolvers = new ArrayList<>();

        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new PathVariableMethodArgumentResolver());
        resolvers.add(new RequestResponseBodyMethodProcessor());
    }
}
