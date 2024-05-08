package com.milktea.myspring.boot.web.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.Map;

public interface HandlerMapping {
    Method getHandler(HttpServletRequest request);
}
