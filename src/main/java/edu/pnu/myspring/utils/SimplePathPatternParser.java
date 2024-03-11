package edu.pnu.myspring.utils;

import com.pnu.demo.controllers.StudentController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimplePathPatternParser {
    //중괄호 내부에 슬래시가 포함되지 않는 문자를 찾음 /student/{id}에서 id를 반환
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{([^/]+?)\\}");
    private final String pattern;
    private final Map<String, Integer> variableIndexes;


    public SimplePathPatternParser(String pattern) {
        this.pattern = pattern;

        Matcher matcher = VARIABLE_PATTERN.matcher(pattern);
        //variableIndexes에서 pathVariable들의 이름과 index가 저장됨
        variableIndexes = new HashMap<>();

        int index = 0;
        while (matcher.find()) {
            String variable = matcher.group(1);
            variableIndexes.put(variable, index++);
        }
    }

    //match로 pattern에 해당하는 uri인지 찾을 수 있음

    //찾지 못하면 null값을 반환
    public boolean match(String path) {
        String[] pathSegments = path.split("/");
        String[] patternSegments = pattern.split("/");

        if (pathSegments.length != patternSegments.length) {
            return false;
        }

        for (int i = 0; i < pathSegments.length; i++) {
            String pathSegment = pathSegments[i];
            String patternSegment = patternSegments[i];

            if (patternSegment.startsWith("{") && patternSegment.endsWith("}")) continue;
            else if (!pathSegment.equals(patternSegment)) {
                return false;
            }
        }

        return true;
    }

    public Map<String, Object> getPathVariables(Method method, String path) {
        String[] pathSegments = path.split("/");
        String[] patternSegments = pattern.split("/");

        Map<String, Object> uriVariables = new HashMap<>();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < pathSegments.length; i++) {
            String pathSegment = pathSegments[i]; //{2}
            String patternSegment = patternSegments[i]; //{id}

            if (patternSegment.startsWith("{") && patternSegment.endsWith("}")) {
                String variable = patternSegment.substring(1, patternSegment.length() - 1);
                int variableIndex = variableIndexes.get(variable);

                Class<?> clazz = method.getDeclaringClass();

                if (clazz == StudentController.class && method.getName().equals("getStudent")) {
                    if (variable.equals("id")) uriVariables.put(variable, castToParameterType(Long.class, pathSegments[i]));
                }
                /*
                //pathVariable이 메서드에서 어떤 타입인지 찾기
                for (Parameter parameter : parameters) {
                    if (parameter.getName().equals(variable)) {
                        uriVariables.put(variable, castToParameterType(parameter.getType(), pathSegments[i]));
                    }
                }
                 */


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