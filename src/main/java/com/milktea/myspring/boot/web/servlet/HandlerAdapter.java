package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public interface HandlerAdapter {
    void handle(HttpServletRequest request, HttpServletResponse response, Method handler);
}
