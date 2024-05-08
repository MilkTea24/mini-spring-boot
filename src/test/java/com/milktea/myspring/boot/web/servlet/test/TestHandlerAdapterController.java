package com.milktea.myspring.boot.web.servlet.test;

import com.milktea.myspring.annotations.GetMapping;
import com.milktea.myspring.annotations.PathVariable;
import com.milktea.myspring.annotations.RequestParam;
import com.milktea.myspring.annotations.RestController;

@RestController
public class TestHandlerAdapterController {
    @GetMapping("/user/{userId}/order/{orderId}")
    public OrderResponseDto getUser(@PathVariable Integer userId, @PathVariable Long orderId, @RequestParam("details") boolean isDetails) {
        if (isDetails) {
            return new OrderResponseDto(userId, orderId);
        }
        return null;
    }
}
