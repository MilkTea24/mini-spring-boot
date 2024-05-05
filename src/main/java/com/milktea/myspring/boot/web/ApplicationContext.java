package com.milktea.myspring.boot.web;

public interface ApplicationContext {
    void refresh(String basePackage);

    void close();
}
