package com.milktea.myspring.boot.web;

import com.milktea.myspring.annotations.SpringBootApplication;
import com.milktea.myspring.boot.web.ioc.AnnotationConfigApplicationContext;
import com.milktea.myspring.boot.web.ioc.ApplicationContext;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class SpringApplication {
        public static final int PORT = 9090;
        public static ApplicationContext run(Class<?> clazz, String... args) {

            if (!clazz.isAnnotationPresent(SpringBootApplication.class)) {
                throw new RuntimeException("The class must be annotated with @MySpringApplication");
            }

            java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.WARNING);

            ApplicationContext context = createApplicationContext(clazz);

            TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
            Tomcat tomcat = serverFactory.getWebServer(clazz, context);

            startServer(tomcat);

            return context;
        }

        private static ApplicationContext createApplicationContext(Class<?> clazz) {
            String basePackage = clazz.getPackageName();
            ApplicationContext context = new AnnotationConfigApplicationContext();
            context.refresh(basePackage);
            return context;
        }

        private static void startServer(Tomcat tomcat) {
            Runnable task = () -> {
                try {
                    tomcat.start();

                    displayBanner();
                } catch (Exception e) {

                    e.printStackTrace();

                }

            };

            new Thread(task).start();
        }



        private static void displayBanner() {

            String banner =

                    """
    
                        ╭━╮╭━╮╱╱╱╭━━━╮╱╱╱╱╱╱╱╱╱╱╱╭━━╮╱╱╱╱╱╱╭╮
                        ┃┃╰╯┃┃╱╱╱┃╭━╮┃╱╱╱╱╱╱╱╱╱╱╱┃╭╮┃╱╱╱╱╱╭╯╰╮
                        ┃╭╮╭╮┣╮╱╭┫╰━━┳━━┳━┳┳━╮╭━━┫╰╯╰┳━━┳━┻╮╭╯
                        ┃┃┃┃┃┃┃╱┃┣━━╮┃╭╮┃╭╋┫╭╮┫╭╮┃╭━╮┃╭╮┃╭╮┃┃
                        ┃┃┃┃┃┃╰━╯┃╰━╯┃╰╯┃┃┃┃┃┃┃╰╯┃╰━╯┃╰╯┃╰╯┃╰╮
                        ╰╯╰╯╰┻━╮╭┻━━━┫╭━┻╯╰┻╯╰┻━╮┣━━━┻━━┻━━┻━╯
                        ╱╱╱╱╱╭━╯┃╱╱╱╱┃┃╱╱╱╱╱╱╱╭━╯┃
                        ╱╱╱╱╱╰━━╯╱╱╱╱╰╯╱╱╱╱╱╱╱╰━━╯
    
                    """;

            System.out.println(banner);

        }
}
