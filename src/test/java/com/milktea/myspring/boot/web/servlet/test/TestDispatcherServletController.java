package com.milktea.myspring.boot.web.servlet.test;

import com.milktea.myspring.annotations.*;

@RestController
public class TestDispatcherServletController {
    @GetMapping("/user/{userId}/order/{orderId}")
    public OrderResponseDto getUser(@PathVariable Integer userId, @PathVariable Long orderId, @RequestParam("details") boolean isDetails) {
        if (isDetails) {
            return new OrderResponseDto(userId, orderId);
        }
        return null;
    }

    @PostMapping("/user/{userId}")
    public OrderResponseDto postUser(@RequestBody UserRequestDto user, @PathVariable Integer userId) {
        return new OrderResponseDto(user.getId().intValue(), 2L);
    }
}
