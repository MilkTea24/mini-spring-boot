package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.PostMapping;
import com.milktea.myspring.boot.web.utils.SimplePathPatternParser;
import com.milktea.myspring.annotations.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandlerMapping implements HandlerMapping {
    private final ControllerRegistry controllerRegistry;


    public RequestMappingHandlerMapping(ControllerRegistry controllerRegistry) {
        this.controllerRegistry = controllerRegistry;
    }

    @Override
    public Method getHandler(HttpServletRequest request) {
        Map<Class<?>, Object> controllerBeans = controllerRegistry.getControllerBeans();

        Method handler = null;

        for (Map.Entry<Class<?>, Object> entry : controllerBeans.entrySet()) {
            Class<?> controllerClass = entry.getKey();

            Method[] methods = controllerClass.getDeclaredMethods();
            handler = processMappingAnnotation(request, methods);
            if (handler != null) break;
        }

        if (handler == null) throw new RuntimeException("Request에 대응하는 핸들러를 찾을 수 없습니다 : " + request.getMethod() + " " + request.getRequestURI());
        return handler;
    }

    private Method processMappingAnnotation(HttpServletRequest request, Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                if (processRequestMapping(method, request)) return method;
            }
            else if (method.isAnnotationPresent(PostMapping.class)) {
                if (processPostMapping(method, request)) return method;
            }
            else if (method.isAnnotationPresent(GetMapping.class)) {
                if (processGetMapping(method, request)) return method;
            }
        }
        return null;
    }

    private boolean processRequestMapping(Method m, HttpServletRequest request) {
        RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);

        return match(request, requestMapping.method(), requestMapping.value());
    }

    private boolean processPostMapping(Method m, HttpServletRequest request) {
        PostMapping postMapping = m.getAnnotation(PostMapping.class);

        return match(request, "POST", postMapping.value());
    }

    private boolean processGetMapping(Method m, HttpServletRequest request) {
        GetMapping getMapping = m.getAnnotation(GetMapping.class);

        return match(request, "GET", getMapping.value());
    }

    private boolean match(HttpServletRequest request, String mappingMethod, String mappingPath) {
        if (!mappingMethod.equals(request.getMethod().toUpperCase())) return false;

        SimplePathPatternParser pathPatternParser = new SimplePathPatternParser(mappingPath);

        return SimplePathPatternParser.match(mappingPath, request.getRequestURI());
    }

}
