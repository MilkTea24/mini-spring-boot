package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import com.milktea.myspring.boot.web.ioc.SingletonBeanFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class ServletInvocableHandlerMethod {
    Method handler;

    ApplicationContext context;

    List<HandlerMethodArgumentResolver> resolvers;
    public ServletInvocableHandlerMethod(Method handler, List<HandlerMethodArgumentResolver> resolvers) {
        this.handler = handler;
        this.resolvers = resolvers;
    }

    public void invokeAndHandle(HttpServletRequest request, HttpServletResponse response) {
        Object returnValue = invokeForRequest(request);

        //handleForResponse(response);
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
            }
        }
        return results;
    }

    private Object doInvoke(Object[] args) {
        try {
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
