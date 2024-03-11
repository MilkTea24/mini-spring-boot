package edu.pnu.myspring.dispatcher;

import java.lang.reflect.Method;
import java.util.Objects;

public class MyHandlerAdapter {
    private final MyHandlerMapping myHandlerMapping;

    public MyHandlerAdapter(MyHandlerMapping myHandlerMapping) {
        this.myHandlerMapping = myHandlerMapping;
    }

    //컨트롤러에게 보내고 결과값을 다시 UserResponse로 받아야 한다.
    public MyUserResponse handle(UserRequest userRequest, Method handler, Object[] args) {
        Object controllerInstance = myHandlerMapping.getControllerInstance(userRequest);
        Class<?> returnType = handler.getReturnType();


        MyUserResponse userResponse = null;

        try {
            handler.setAccessible(true);
            Object resolve = handler.invoke(controllerInstance, args);
            String body = "";
            if (!Objects.isNull(resolve)) body = returnType.cast(resolve).toString();
            userResponse = new MyUserResponse(200, body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userResponse;
    }
}
