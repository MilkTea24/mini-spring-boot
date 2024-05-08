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
}
