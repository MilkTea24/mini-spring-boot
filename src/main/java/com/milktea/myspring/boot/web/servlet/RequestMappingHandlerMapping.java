package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.annotations.Component;
import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.PostMapping;
import com.milktea.myspring.boot.web.utils.SimplePathPatternParser;
import com.milktea.myspring.annotations.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping {
    private final ControllerRegistry controllerRegistry;
    private boolean pathVariableFlag = false;
    private Map<String, Object> pathVariables = new HashMap<>();
    private Class<?> controllerClass;


    public RequestMappingHandlerMapping(ControllerRegistry controllerRegistry) {
        this.controllerRegistry = controllerRegistry;
    }

    @Override
    public Method getHandler(HttpServletRequest request) {
        Map<Class<?>, Object> controllerBeans = controllerRegistry.getControllerBeans();

        Method handler = null;

        for (Map.Entry<Class<?>, Object> entry : controllerBeans.entrySet()) {
            controllerClass = entry.getKey();

            Method[] methods = controllerClass.getDeclaredMethods();
            handler = processMappingAnnotation(request, methods);
        }

        if (handler == null) throw new RuntimeException("Request에 대응하는 핸들러를 찾을 수 없습니다 : " + request.getMethod() + " " + request.getRequestURI());
        return handler;
    }

    private Method processMappingAnnotation(HttpServletRequest request, Method[] methods) {
        for (Method m : methods) {
            if (m.isAnnotationPresent(RequestMapping.class)) {
                if (processRequestMapping(m, request)) return m;
            }
            else if (m.isAnnotationPresent(PostMapping.class)) {
                if (processPostMapping(m, request)) return m;
            }
            else if (m.isAnnotationPresent(GetMapping.class)) {
                if (processGetMapping(m, request)) return m;
            }
        }
    }

    private boolean processRequestMapping(Method m, HttpServletRequest request) {
        RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);

        return getPathVariables(m, request, requestMapping.method(), requestMapping.value());
    }

    private boolean processPostMapping(Method m, HttpServletRequest request) {
        PostMapping postMapping = m.getAnnotation(PostMapping.class);

        return getPathVariables(m, request, "POST", postMapping.value());
    }

    private boolean processGetMapping(Method m, HttpServletRequest request) {
        GetMapping getMapping = m.getAnnotation(GetMapping.class);

        return getPathVariables(m, request, "GET", getMapping.value());
    }

    private boolean getPathVariables(Method m, HttpServletRequest request, String mappingMethod, String mappingPath) {
        if (!mappingMethod.equals(request.getMethod().toUpperCase())) return false;

        SimplePathPatternParser pathPatternParser = new SimplePathPatternParser(mappingPath);

        if (pathPatternParser.match(request.getRequestURI())) {
            pathVariables = pathPatternParser.getPathVariables(m, request.getRequestURI());
            pathVariableFlag = true;
            return true;
        }
        else return false;
    }

}
