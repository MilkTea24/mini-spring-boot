package com.milktea.myspring.boot.web.ioc;

public interface ApplicationContext {
    void refresh(String basePackage);

    void close();
}
