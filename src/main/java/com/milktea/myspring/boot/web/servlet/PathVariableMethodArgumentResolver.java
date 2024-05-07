package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.PathVariable;
import com.milktea.myspring.annotations.PostMapping;
import com.milktea.myspring.annotations.RequestMapping;
import com.milktea.myspring.boot.web.utils.SimplePathPatternParser;
import com.milktea.myspring.boot.web.utils.converters.ConversionService;
import com.milktea.myspring.boot.web.utils.converters.DefaultConversionService;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class PathVariableMethodArgumentResolver implements HandlerMethodArgumentResolver {
    ConversionService conversionService = DefaultConversionService.getInstance();

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(Parameter parameter, HttpServletRequest request) {
        Method declaringMethod = (Method)parameter.getDeclaringExecutable();

        String pattern = null;
        if (declaringMethod.isAnnotationPresent(RequestMapping.class)) {
            pattern = declaringMethod.getAnnotation(RequestMapping.class).value();
        }
        else if (declaringMethod.isAnnotationPresent(GetMapping.class)) {
            pattern = declaringMethod.getAnnotation(GetMapping.class).value();
        }
        else if (declaringMethod.isAnnotationPresent(PostMapping.class)) {
            pattern = declaringMethod.getAnnotation(PostMapping.class).value();
        }

        Object value = SimplePathPatternParser.getPathVariable(parameter, pattern, request.getRequestURI());

        return conversionService.convert(value, parameter.getType());
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
