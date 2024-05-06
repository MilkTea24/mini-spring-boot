package com.milktea.myspring.boot.web.utils;

import com.milktea.myspring.annotations.PathVariable;

public class TestController {
    public void successMethod(@PathVariable int userId, @PathVariable long orderId) {}

    public void failMethod(@PathVariable int invalidParameter, @PathVariable long orderId) {}

    public void successValueMethod(@PathVariable("userId") int user, @PathVariable("orderId") long order) {}
}
