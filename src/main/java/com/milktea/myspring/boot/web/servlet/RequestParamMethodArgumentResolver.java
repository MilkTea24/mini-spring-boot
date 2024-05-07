package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.annotations.PathVariable;
import com.milktea.myspring.annotations.RequestBody;
import com.milktea.myspring.annotations.RequestParam;
import com.milktea.myspring.boot.web.utils.converters.ConversionService;
import com.milktea.myspring.boot.web.utils.converters.DefaultConversionService;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {
    ConversionService conversionService = DefaultConversionService.getInstance();
    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class) ||
                //@PathVaraible, @RequestBody 모두 붙어있지 않으면 RequestParam에서 처리
                (!parameter.isAnnotationPresent(PathVariable.class) && !parameter.isAnnotationPresent(RequestBody.class));
    }

    @Override
    public Object resolveArgument(Parameter parameter, HttpServletRequest request) {
        if (!parameter.isAnnotationPresent(RequestParam.class)) return resolveDefaultParameter(parameter, request);
        else return resolveRequestParamParameter(parameter, request);
    }

    private Object resolveDefaultParameter(Parameter parameter, HttpServletRequest request) {
        String value = request.getParameter(parameter.getName());

        return conversionService.convert(value, parameter.getType());
    }

    private Object resolveRequestParamParameter(Parameter parameter, HttpServletRequest request) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);

        String parameterName = requestParam.name().isBlank() ? parameter.getName() : requestParam.name();
        String requestParameter = request.getParameter(parameterName);

        if (requestParameter.isBlank() && requestParam.required()) {
            throw new RuntimeException("해당하는 Parameter를 바인딩할 수 없습니다. : " + parameterName);
        }

        if (requestParameter.isBlank() && !requestParam.defaultValue().isBlank()) {
            return conversionService.convert(requestParam.defaultValue(), parameter.getType());
        }

        return conversionService.convert(requestParameter, parameter.getType());
    }
}
