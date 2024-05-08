package com.milktea.myspring.boot.web.servlet.test;

import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.PathVariable;
import com.milktea.myspring.annotations.RestController;

@RestController
public class TestPathVariableController {
    @GetMapping("/user/{userId}/order/{orderId}")
    public void defaultHandler(@PathVariable Integer userId, @PathVariable Long orderId) {
        System.out.println("defaultHandler invoked");
    }

    @GetMapping("/user/{userId}/order/{orderId}")
    public void valueHandler(@PathVariable("userId") Integer user, @PathVariable("orderId") Long order) {
        System.out.println("valueHandler invoked");
    }

    @GetMapping("/user/{userId}/order/{orderId}")
    public void invalidArgumentHandler(@PathVariable Integer invalidName, @PathVariable("orderId") Long order) {
        System.out.println("invalidArgumentHandler invoked");
    }
}
