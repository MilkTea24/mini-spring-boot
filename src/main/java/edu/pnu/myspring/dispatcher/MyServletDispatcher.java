package edu.pnu.myspring.dispatcher;

import edu.pnu.myspring.core.MyApplicationContext;
import edu.pnu.myspring.utils.ControllerRegistry;
import edu.pnu.myspring.utils.MyJsonParser;

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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="MyServletDispatcher", urlPatterns = "/*")

public class MyServletDispatcher extends HttpServlet {
    private final MyHandlerMapping handlerMapping;

    private final MyHandlerAdapter handlerAdapter;

    @Override

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        System.out.println("ServletContext found!");
        System.out.println("MyServletDispatcher.init");


    }

    public MyServletDispatcher(MyApplicationContext context) {
        this.handlerMapping = new MyHandlerMapping(new ControllerRegistry(context));

        this.handlerAdapter = new MyHandlerAdapter(handlerMapping);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = "POST";
        String requestURL = req.getRequestURI();
        String requestBody = getBody(req);

        System.out.println("MyServletDispatcher.doPost: " + req.getRequestURI());

        UserRequest userRequest = new UserRequest(requestMethod, requestURL, requestBody);
        //System.out.printf("requestMethod : %s\n", requestMethod);
        //System.out.printf("requestUrl : %s\n", requestURL);
        //System.out.printf("requestBody : %s\n", requestBody);
        dispatch(userRequest, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = "GET";
        String requestURL = req.getRequestURI();

        System.out.println("MyServletDispatcher.doGet: " + req.getRequestURI());

        UserRequest userRequest = new UserRequest(requestMethod, requestURL, "");
        dispatch(userRequest, resp);
    }

    private void dispatch(UserRequest userRequest, HttpServletResponse resp) {
        try {
            Method handler = handlerMapping.getHandler(userRequest.getMethod(), userRequest.getUri());

            if (handler == null) {

                System.out.println("ERROR: No mapping found for " + userRequest.getMethod() + " " + userRequest.getUri());

                return;

            }

            Map<String, Object> params = new HashMap<>();

            // Handle JSON body for POST and PUT requests

            if (userRequest.getMethod().equals("POST") || userRequest.getMethod().equals("PUT")) {

                String jsonBody = userRequest.getJsonBody();

                if (jsonBody != null && !jsonBody.isEmpty()) {
                    MyJsonParser jsonParser = new MyJsonParser(jsonBody);

                    params.putAll((HashMap<String, Object>)jsonParser.parseJsonString()); // MyJsonParser is a hypothetical parser you'd need to implement
                }

            }

            // Extract path variables from the request URI (쉬운 방법으로, 예를 들어, 메서드 파라미터명을 미리 등록해 놓음, 구현하셔도 됩니다!)

            Map<String, Object> pathVariables = handlerMapping.extractPathVariables(userRequest);

            params.putAll(pathVariables);

            // Extract args based on the extracted path variables and method signature

            Object[] args = handlerMapping.extractArgsForMethod(handler, params);



            MyUserResponse response = handlerAdapter.handle(userRequest, handler, args);

            /* 과제7
            System.out.println("Response: " + response);
             */

            //과제 10
            System.out.println("Response: " + response.getBody());

            resp.getWriter().write(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("ERROR: " + e.getMessage());

        }
    }

    private String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
            if (inputStream != null) {
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        body = stringBuilder.toString();

        return body;
    }

    public void startListening() {

    }


    // implement your code

}