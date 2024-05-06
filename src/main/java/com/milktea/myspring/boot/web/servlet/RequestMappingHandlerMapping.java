package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.annotations.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMappingHandlerMapping {
    //private final ControllerRegistry controllerRegistry;
    private boolean pathVariableFlag = false;
    private Map<String, Object> pathVariables = new HashMap<>();
    private Class<?> controllerClass;

    /*
    public RequestMappingHandlerMapping(ControllerRegistry controllerRegistry) {
        this.controllerRegistry = controllerRegistry;
    }

    @Override
    public Method getHandler(HttpServletRequest request) {
        Map<Class<?>, Object> controllerBeans = controllerRegistry.getControllerBeans();
        if (controllerBeans.isEmpty()) System.out.println("컨트롤러빈 비었음");

        for (Map.Entry<Class<?>, Object> entry : controllerBeans.entrySet()) {
            controllerClass = entry.getKey();

            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(m, RequestMapping.class)) {
                    processRequestMapping(request);
                }
                else if (m.isAnnotationPresent(PostMapping.class)) {
                    processPostMapping(m);
                }
            }
        }

        return null;
    }

    private void processRequestMapping(Method m, HttpServletRequest request) {
        RequestMapping myRequestMapping = m.getAnnotation(RequestMapping.class);
        String mappingPath = myRequestMapping.value();
        String mappingMethod = myRequestMapping.method();
        String requestMethod = request.getMethod().toUpperCase();

        if (!mappingMethod.equals(requestMethod)) return;

        SimplePathPatternParser pathPatternParser = new SimplePathPatternParser(mappingPath);

        if (pathPatternParser.match(uri)) {
            pathVariables = pathPatternParser.getPathVariables(m, uri);
            pathVariableFlag = true;
        }
    }

    private void processPostMapping(Method m) {
        PostMapping postMapping = m.getAnnotation(PostMapping.class);
        String mappingPath = postMapping.value();
        //System.out.println("Method " + m.getName() + ": " + mappingPath);
        String mappingMethod = postMapping.method();
        String requestMethod = method.toUpperCase();

        if (!mappingMethod.equals(requestMethod)) return;
        SimplePathPatternParser pathPatternParser = new SimplePathPatternParser(mappingPath);

        if (pathPatternParser.match(uri)) {
            pathVariables = pathPatternParser.getPathVariables(m, uri);
            pathVariableFlag = true;
        }
    }

    //json 또는 pathVariable로 전달된 데이터를 추출해야함
    //params는 pathVariable 포함해서 입력받은 key Value를 모두 저장해놓은 맵
    public Object[] extractArgsForMethod(Method handler, Map<String, Object> params) {
        List<Object> args = new ArrayList<>();
        Parameter[] parameters = handler.getParameters();
        Class<?> clazz = handler.getDeclaringClass();


        if (clazz == StudentController.class && handler.getName().equals("getStudent")) {
            args.add(params.get("id"));
        }
        else if (clazz == StudentController.class && handler.getName().equals("createStudent")) {
            args.add(params.get("name"));
            args.add(params.get("course"));
        }

        //Method의 모든 매개변수를 사용자가 입력한 이름과 비교하여 사용자가 입력한 이름이 있으면
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            args.add(params.get(name));
        }

        return args.toArray();
    }

    //"/students/{id}"와 같은 PathVariable을 구현해야 함
    public Map<String, Object> extractPathVariables(UserRequest userRequest) {
        if (!pathVariableFlag) {
            if (getHandler(userRequest.getMethod(), userRequest.getUri()) == null){
                System.out.println("ERROR: No mapping found for " + userRequest.getMethod() + " " + userRequest.getUri());
                return null;
            }
        }

        return pathVariables;
    }

    //매핑된 컨트롤러를 찾아야함
    public Method getHandler(String method, String uri) {

    }

    public Class<?> getControllerClass(UserRequest userRequest) {
        if (controllerClass == null) {
            if (getHandler(userRequest.getMethod(), userRequest.getUri()) == null){
                System.out.println("ERROR: No mapping found for " + userRequest.getMethod() + " " + userRequest.getUri());
                return null;
            }
        }

        return controllerClass;
    }

    public Object getControllerInstance(UserRequest userRequest) {
        if (controllerClass == null) {
            if (getHandler(userRequest.getMethod(), userRequest.getUri()) == null){
                System.out.println("ERROR: No mapping found for " + userRequest.getMethod() + " " + userRequest.getUri());
                return null;
            }
        }

        return controllerRegistry.getControllerBeans().get(controllerClass);
    }

     */
}
