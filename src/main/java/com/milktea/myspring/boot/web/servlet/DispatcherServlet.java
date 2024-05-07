package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

@WebServlet(name="MyDispatcherServlet", urlPatterns = "/*")

public class DispatcherServlet extends HttpServlet {
    private final ApplicationContext context;

    //웹 요청을 처리할 수 있는 핸들러(컨트롤러 메서드)를 찾는 역할
    private HandlerMapping handlerMapping;

    //찾은 핸들러를 실행하고 응답 생성을 지원
    //private HandlerAdapter handlerAdapter;

    @Override

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public DispatcherServlet(ApplicationContext context) {
        this.context = context;

        this.handlerMapping = new RequestMappingHandlerMapping(new ControllerRegistry(context));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = "POST";
        String requestURL = req.getRequestURI();

        System.out.println("MyServletDispatcher.doPost: " + req.getRequestURI());

        //UserRequest userRequest = new UserRequest(requestMethod, requestURL, requestBody);
        //System.out.printf("requestMethod : %s\n", requestMethod);
        //System.out.printf("requestUrl : %s\n", requestURL);
        //System.out.printf("requestBody : %s\n", requestBody);
        //dispatch(userRequest, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = "GET";
        String requestURL = req.getRequestURI();

        System.out.println("MyServletDispatcher.doGet: " + req.getRequestURI());

        //UserRequest userRequest = new UserRequest(requestMethod, requestURL, "");
        //dispatch(userRequest, resp);
    }

    //doService 내부에 포함
    private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        //1. 핸들러 조회
        Method handler = handlerMapping.getHandler(request);

        //2. 핸들러 어댑터 조회(현재는 핸들러 어댑터 전략이 하나밖에 없으므로 바로 반환)
        HandlerAdapter adapter = new RequestMappingHandlerAdapter(context);

        //3. 핸들러 어댑터의 핸들러 호출
        adapter.handle(request, response, handler);

        //4. 핸들러 어댑터가 반환한 값 반환

    }




    public void startListening() {

    }


    // implement your code

}
