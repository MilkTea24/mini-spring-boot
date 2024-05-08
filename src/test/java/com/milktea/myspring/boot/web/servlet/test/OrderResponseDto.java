package com.milktea.myspring.boot.web.servlet.test;

public class OrderResponseDto {
    private Integer userId;
    private Long orderId;

    public OrderResponseDto(Integer userId, Long orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
