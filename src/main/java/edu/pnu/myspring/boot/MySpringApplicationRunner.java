package edu.pnu.myspring.boot;

import edu.pnu.myspring.annotations.MySpringApplication;
import edu.pnu.myspring.core.MyApplicationContext;
import edu.pnu.myspring.dispatcher.MyServletDispatcher;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class MySpringApplicationRunner {
    public static MyApplicationContext run(Class<?> clazz, String... args) {

        if (!clazz.isAnnotationPresent(MySpringApplication.class)) {

            throw new RuntimeException("The class must be annotated with @MySpringApplication");

        }

        java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.WARNING);



        Tomcat tomcat = new Tomcat();

        tomcat.setPort(9090);

        tomcat.getConnector(); // Trigger the creation of the default connector



        String contextPath = ""; // For ROOT context

        String appBasePath = ".";   // Relative path indicating current directory

        File appBase = new File(appBasePath);

        //System.out.println("docBase: " + appBase.getAbsolutePath());

        Context ctx = tomcat.addWebapp(contextPath, appBase.getAbsolutePath());

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ctx.setParentClassLoader(classLoader);


        String basePackage = clazz.getPackageName();
        System.out.println(basePackage);

        MyApplicationContext context = new MyApplicationContext(basePackage);

        Tomcat.addServlet(ctx, "MyServletDispatcher", new MyServletDispatcher(context));

        ctx.addServletMappingDecoded("/*", "MyServletDispatcher");

        ctx.addApplicationListener(MyServletContextListener.class.getName());


        Runnable task = () -> {

            try {
                tomcat.start();

                System.out.println("Tomcat started!");
                displayBanner();

                //tomcat.getServer().await();

                Thread.sleep(10000);

                //System.out.println("Stopping Tomcat");

                tomcat.stop();

            } catch (Exception e) {

                e.printStackTrace();

            }

        };

        new Thread(task).start();


        // implement your code




        return context;

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




    /* 과제 7
    public static MyApplicationContext run(Class<?> clazz, String... args) {

        if (!clazz.isAnnotationPresent(MySpringApplication.class)) {

            throw new RuntimeException("The class must be annotated with @MySpringApplication");

        }



        //displayBanner();

        String basePackage = clazz.getPackageName();

        MyApplicationContext context = new MyApplicationContext(basePackage);



        RequestDispatcher dispatcher = new RequestDispatcher(context, new InputProvider());

        dispatcher.startListening();



        return context;

    }
    */
}
