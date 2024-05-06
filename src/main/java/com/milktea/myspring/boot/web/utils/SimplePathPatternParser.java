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
    //중괄호 내부에 슬래시가 포함되지 않는 문자를 찾음 /student/{id}에서 id를 반환
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

    public Map<String, Object> getPathVariables(Method method, String path) {
        String[] pathSegments = path.split("/");

        Map<String, Object> uriVariables = new HashMap<>();
        Parameter[] parameters = method.getParameters();

        for (Map.Entry<String, Integer> entry : pathVariables.entrySet()) {
            String pathVariableName = entry.getKey().substring(1, entry.getKey().length() - 1);

            boolean hasProperParameter = false;
            for (Parameter parameter : parameters) {
                if (!parameter.isAnnotationPresent(PathVariable.class)) continue;
                PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                String value = pathVariable.value();
                //PathVariable에서 value 값이 없으면 파라미터 이름과 비교
                if (value.isEmpty() && pathVariableName.equals(parameter.getName()))
                    uriVariables.put(pathVariableName, castToParameterType(parameter.getType(), pathSegments[entry.getValue()]));
            }
        }


        for (int i = 0; i < pathSegments.length; i++) {
            String patternSegment = patternSegments.get(i);

            //pathVariable 형태를 가진 것만 확인({id})
            if (!(patternSegment.startsWith("{") && patternSegment.endsWith("}"))) continue;

            String variable = patternSegment.substring(1, patternSegment.length() - 1);
            //pathVariable이 메서드에서 어떤 타입인지 찾기
            for (Parameter parameter : parameters) {
                if (!parameter.isAnnotationPresent(PathVariable.class)) continue;
                if (!parameter.getName().equals(variable)) continue;

                uriVariables.put(variable, castToParameterType(parameter.getType(), pathSegments[i]));
            }
        }

        return uriVariables;
    }

    private Object castToParameterType(Class<?> type, String variable) {
        if (type.getName().equals("java.lang.String")) return variable;
        else if (type.getName().equals("java.lang.Long")) return Long.parseLong(variable);
        else if (type.getName().equals("java.lang.Integer")) return Integer.parseInt(variable);
        else return variable;
    }


    //uriVariables에 pathVariable의 값들이 저장되어 있음
    //Key는 PathVariable의 이름, Value는 PathVariable의
}
