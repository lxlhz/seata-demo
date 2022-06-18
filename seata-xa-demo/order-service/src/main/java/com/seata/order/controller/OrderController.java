package com.seata.order.controller;

import com.seata.order.entity.Order;
import com.seata.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LiGezZ
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody Order order) {
        Long orderId = orderService.create(order);
        return ResponseEntity.status(HttpStatus.OK).body(orderId);
    }
}
