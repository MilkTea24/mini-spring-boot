package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;

public interface HandlerMapping {
    Method getHandler(HttpServletRequest request);
}
