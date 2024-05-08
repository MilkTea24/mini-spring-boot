package com.milktea.myspring.boot.web.servlet.test;

import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.RequestParam;
import com.milktea.myspring.annotations.RestController;

@RestController
public class TestRequestParamController {
    @GetMapping("/hello1?userId=1&orderId=2")
    public void defaultHandler(Integer userId, Long orderId) {
        System.out.println("defaultHandler invoked");
    }

    @GetMapping("/hello2?userId=1&orderId=2")
    public void requestParamDefaultHandler(@RequestParam Integer userId, @RequestParam Long orderId) {
        System.out.println("requestParamDefaultHandler invoked");
    }

    @GetMapping("/hello3?userId=1&orderId=2")
    public void requestParamValueHandler(@RequestParam("userId") Integer user, @RequestParam("orderId") Long order) {
        System.out.println("requestParamValueHandler invoked");
    }

    @GetMapping("/hello4?userId=1")
    public void requestParamRequiredFalseHandler(@RequestParam Integer userId, @RequestParam(required = false) Long orderId) {
        System.out.println("requestParamRequiredFalseHandler invoked");
    }

    @GetMapping("/hello5?userId=1")
    public void requestParamRequiredTrueHandler(@RequestParam Integer userId, @RequestParam Long orderId) {
        System.out.println("requestParamRequiredTrueHandler invoked");
    }

    @GetMapping("/hello6?userId=1")
    public void requestParamDefaultValueHandler(@RequestParam Integer userId, @RequestParam(required = false, defaultValue = "2") Long orderId) {
        System.out.println("requestParamDefaultValueHandler invoked");
    }
}
