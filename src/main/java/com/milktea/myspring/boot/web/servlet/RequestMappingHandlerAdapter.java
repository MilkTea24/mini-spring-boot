package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

    private void getDefaultArgumentResolvers() {
        resolvers = new ArrayList<>();

        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new PathVariableMethodArgumentResolver());
        resolvers.add(new RequestBodyMethodArgumentResolver());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Method handler) {
        checkRequest(request);

        ServletInvocableHandlerMethod invocableMethod = new ServletInvocableHandlerMethod(handler, resolvers);

        invocableMethod.invokeAndHandle(request, response);
    }

    private void checkRequest(HttpServletRequest request) {

    }

    private void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Method handler) {
        WebDataBinderFactory binderFactory = new DefaultWebDataBinderFactory();

       ServletInvocableHandlerMethod invocableMethod =
    }
    //json 또는 pathVariable로 전달된 데이터를 추출해야함
    //params는 pathVariable 포함해서 입력받은 key Value를 모두 저장해놓은 맵
    /*
    public Object[] extractArgsForMethod(Method handler, Map<String, Object> params) {
        if (!pathVariableFlag) getHandler(request);

        List<Object> args = new ArrayList<>();
        Parameter[] parameters = handler.getParameters();
        Class<?> clazz = handler.getDeclaringClass();

        SimplePathPatternParser

        return args.toArray();
    }

    //"/students/{id}"와 같은 PathVariable을 구현해야 함
    public Map<String, Object> extractPathVariables(HttpServletRequest request) {
        if (!pathVariableFlag && getHandler(request) == null) {
            throw new RuntimeException("Request에 대응하는 핸들러를 찾을 수 없습니다 : " + request.getMethod() + " " + request.getRequestURI());
        }

        return pathVariables;
    }

    public Object getControllerInstance(HttpServletRequest request) {
        if (controllerClass == null && getHandler(request) == null){
            throw new RuntimeException("Request에 대응하는 핸들러를 찾을 수 없습니다 : " + request.getMethod() + " " + request.getRequestURI());
        }

        return controllerRegistry.getControllerBeans().get(controllerClass);
    }
     */
}
