package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class ServletInvocableHandlerMethod {
    Method handler;

    ApplicationContext context;

    List<HandlerMethodArgumentResolver> resolvers;

    List<HandlerMethodReturnValueHandler> returnValueHandlers;

    public ServletInvocableHandlerMethod(Method handler, List<HandlerMethodArgumentResolver> resolvers) {
        this.handler = handler;
        this.resolvers = resolvers;
        this.returnValueHandlers = List.of(new RequestResponseBodyMethodProcessor()); //현재 하나밖에 지원하지 않음
    }

    public void invokeAndHandle(HttpServletRequest request, HttpServletResponse response) {
        Object returnValue = invokeForRequest(request);

        handleForResponse(response, returnValue);
    }

    private void handleForResponse(HttpServletResponse response, Object returnValue) {
        for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
            if (returnValueHandler.supportsReturnType(handler.getReturnType()))
                returnValueHandler.handleReturnValue(returnValue, handler.getReturnType(), response);
        }
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private Object invokeForRequest(HttpServletRequest request) {
        Object[] args = getMethodArgumentValues(request);

        return doInvoke(args);
    }

    private Object[] getMethodArgumentValues(HttpServletRequest request) {
        Parameter[] parameters = handler.getParameters();
        Object[] results = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            for (HandlerMethodArgumentResolver resolver : resolvers) {
                if (!resolver.supportsParameter(parameters[i])) continue;

                results[i] = resolver.resolveArgument(parameters[i], request);
                break;
            }
        }
        return results;
    }

    private Object doInvoke(Object[] args) {
        try {
            System.out.println("invoke " + handler.getName() + " method");
            return handler.invoke(getControllerInstance(handler), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getControllerInstance(Method handler) {
        return context.getBean(handler.getDeclaringClass());
    }
}
