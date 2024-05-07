package com.milktea.myspring.boot.web.utils;

import com.milktea.myspring.annotations.PathVariable;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimplePathPatternParser {
    //슬래시로 분리
    private static final Pattern SEGMENT_PATTERN = Pattern.compile("/([^/]+)");

    public SimplePathPatternParser(String pattern) {

    }

    //match로 pattern에 해당하는 uri인지 찾을 수 있음
    public static boolean match(String pattern, String requestUri) {
        String requestPath = getRequestPath(requestUri);
        List<String> patternSegments = getPatternSegments(pattern);

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

    public static Object getPathVariable(Parameter parameter, String pattern, String requestUri) {
        String requestPath = getRequestPath(requestUri);
        List<String> patternSegments = getPatternSegments(pattern);

        if (requestPath.startsWith("/")) requestPath = requestPath.replaceFirst("/", "");
        String[] pathSegments = requestPath.split("/");

        if (!parameter.isAnnotationPresent(PathVariable.class)) return null;
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);

        String parameterMappingName = pathVariable.value().isBlank() ? parameter.getName() : pathVariable.value();

        for (int i = 0; i < patternSegments.size(); i++) {
            String segment = patternSegments.get(i);
            if (!(segment.startsWith("{") && segment.endsWith("}"))) continue;
            String pathVariableName = segment.substring(1, segment.length() - 1);

            if (pathVariableName.equals(parameterMappingName)) return pathSegments[i];
        }

        throw new RuntimeException("해당하는 PathVariable을 찾을 수 없습니다: " + parameterMappingName);
    }

    private static String getRequestPath(String requestUri) {
        try {
            URI uri = new URI(requestUri);
            return uri.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> getPatternSegments(String pattern) {
        List<String> patternSegments = new ArrayList<>();
        Matcher matcher = SEGMENT_PATTERN.matcher(pattern);

        while (matcher.find()) {
            patternSegments.add(matcher.group(1));
        }

        return patternSegments;
    }
}
