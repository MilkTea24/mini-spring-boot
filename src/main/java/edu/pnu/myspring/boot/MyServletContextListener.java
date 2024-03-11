package edu.pnu.myspring.boot;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener

public class MyServletContextListener implements ServletContextListener {

    private static ServletContext servletContext;

    @Override

    public void contextInitialized(ServletContextEvent sce) {

        servletContext = sce.getServletContext();

        System.out.println("MyServletContextListener.contextInitialized");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    // implement your code

}