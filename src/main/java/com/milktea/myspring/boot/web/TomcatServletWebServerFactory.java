package com.milktea.myspring.boot.web;

import com.milktea.myspring.boot.web.ioc.AnnotationConfigApplicationContext;
import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatServletWebServerFactory {
    private static final int PORT = 9090;
    
    private Tomcat tomcat;
    
    public Tomcat getWebServer(Class<?> clazz, ApplicationContext context) {
        createTomcatServer();

        Context webContext = createWebContext();

        setServletConfigurations(webContext, context);

        return tomcat;
    }

    private static void setServletConfigurations(Context webContext, ApplicationContext context) {
        Tomcat.addServlet(webContext, "MyServletDispatcher", new ServletDispatcher(context));

        webContext.addServletMappingDecoded("/*", "MyServletDispatcher");

        webContext.addApplicationListener(ServletContextListener.class.getName());
    }

    private Context createWebContext() {
        String contextPath = ""; // For ROOT context
        String appBasePath = ".";   // Relative path indicating current directory
        File appBase = new File(appBasePath);
        Context ctx = tomcat.addWebapp(contextPath, appBase.getAbsolutePath());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ctx.setParentClassLoader(classLoader);
        return ctx;
    }

    private void createTomcatServer() {
        tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.getConnector(); // Trigger the creation of the default connector
    }
}
