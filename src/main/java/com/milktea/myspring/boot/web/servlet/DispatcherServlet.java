package com.milktea.myspring.boot.web.servlet;

import com.milktea.myspring.boot.web.ioc.ApplicationContext;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name="MyDispatcherServlet", urlPatterns = "/*")

public class DispatcherServlet extends HttpServlet {
    private final ApplicationContext context;
    private final HandlerMapping handlerMapping;

    public DispatcherServlet(ApplicationContext context) {
        this.context = context;

        this.handlerMapping = new RequestMappingHandlerMapping(new ControllerRegistry(context));
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
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
        //ModelAndView 반환 지원하지 않음
    }

    public void startListening() {

    }

}
