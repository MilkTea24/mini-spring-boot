package com.milktea.myspring.boot.web.utils;

import com.milktea.myspring.annotations.PathVariable;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimplePathPatternParser {
    //슬래시로 분리
    private static final Pattern SEGMENT_PATTERN = Pattern.compile("/([^/]+)");


    //어노테이션에 있는 패턴
    private final String controllerMappingPattern;

    private final List<String> patternSegments = new ArrayList<>();
    private final Map<String, Integer> pathVariables = new HashMap<>();

    public SimplePathPatternParser(String pattern) {
        this.controllerMappingPattern = pattern;

        Matcher matcher = SEGMENT_PATTERN.matcher(pattern);

        while (matcher.find()) {
            patternSegments.add(matcher.group(1));
        }

        for (int i = 0; i < patternSegments.size(); i++) {
            String segment = patternSegments.get(i);
            if (segment.startsWith("{") && segment.endsWith("}")) {
                if (pathVariables.containsKey(segment)) throw new RuntimeException("동일한 pathVariable 이름이 두 개 이상 올 수 없습니다.");

                pathVariables.put(segment, i);
            }
        }
    }

    //match로 pattern에 해당하는 uri인지 찾을 수 있음
    public boolean match(String requestPath) {
        if (requestPath.startsWith("/")) requestPath = requestPath.replaceFirst("/", "");
        String[] pathSegments = requestPath.split("/");

        if (pathSegments.length != patternSegments.size()) return false;

        for (int i = 0; i < pathSegments.length; i++) {
            String pathSegment = pathSegments[i];
            String patternSegment = patternSegments.get(i);

            if (patternSegment.startsWith("{") && patternSegment.endsWith("}")) continue;

            //path variable이 아닌데 서로 segment가 일치하지 않으면 다른 url
            if (!pathSegment.equals(patternSegment)) return false;
        }

        return true;
    }

    public Map<String, Object> getPathVariables(Method method, String requestPath) {
        if (requestPath.startsWith("/")) requestPath = requestPath.replaceFirst("/", "");
        String[] pathSegments = requestPath.split("/");

        Map<String, Object> uriVariables = new HashMap<>();
        Parameter[] parameters = method.getParameters();

        mappingPathVariableAndParameter(parameters, uriVariables, pathSegments);

        return uriVariables;
    }

    private void mappingPathVariableAndParameter(Parameter[] parameters, Map<String, Object> uriVariables, String[] pathSegments) {
        for (Map.Entry<String, Integer> entry : pathVariables.entrySet()) {
            String pathVariableName = entry.getKey().substring(1, entry.getKey().length() - 1);

            boolean hasProperParameter = false;

            //해당 pathVariable에 매핑되는 파라미터 찾기
            for (Parameter parameter : parameters) {
                if (!parameter.isAnnotationPresent(PathVariable.class)) continue;
                PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                String value = pathVariable.value();
                //PathVariable에서 value 값이 없으면 파라미터 이름과 비교
                if (value.isBlank() && pathVariableName.equals(parameter.getName())) {
                    uriVariables.put(pathVariableName, castToParameterType(parameter.getType(), pathSegments[entry.getValue()]));
                    hasProperParameter = true;
                }
                //PathVariable에서 value 값이 있으면 value 값과 비교
                else if (pathVariableName.equals(value)) {
                    uriVariables.put(pathVariableName, castToParameterType(parameter.getType(), pathSegments[entry.getValue()]));
                    hasProperParameter = true;
                }
            }

            if (!hasProperParameter) throw new RuntimeException(pathVariableName + " : pathVariable의 적절한 파라미터 매핑을 찾지 못했습니다.");
        }
    }

    private Object castToParameterType(Class<?> type, String variable) {
        if (type.getName().equals("java.lang.String")) return variable;
        else if (type.getName().equals("java.lang.Long") || type.getName().equals("long")) return Long.parseLong(variable);
        else if (type.getName().equals("java.lang.Integer") || type.getName().equals("int")) return Integer.parseInt(variable);
        else return variable;
    }


    //uriVariables에 pathVariable의 값들이 저장되어 있음
    //Key는 PathVariable의 이름, Value는 PathVariable의
}
