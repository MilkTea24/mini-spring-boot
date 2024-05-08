package com.milktea.myspring.boot.web.servlet.test;

import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.PostMapping;
import com.milktea.myspring.annotations.RequestMapping;
import com.milktea.myspring.annotations.RestController;

@RestController
public class TestController {
    @RequestMapping(value = "/user/{userId}/order/{orderId}")
    public void successMethod() {}

    @GetMapping("/user/{userId}/details")
    public void successGetMethod() {}

    @PostMapping("/user/{userId}/details/order")
    public void successPostMethod() {}
}
