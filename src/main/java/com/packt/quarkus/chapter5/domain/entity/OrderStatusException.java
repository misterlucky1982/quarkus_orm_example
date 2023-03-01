package com.packt.quarkus.chapter5.domain.entity;

public class OrderStatusException extends RuntimeException {
    public OrderStatusException(int code) {
        super("unknown order status code:" + code);
    }
}
