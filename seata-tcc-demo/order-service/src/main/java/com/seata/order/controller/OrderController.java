package com.seata.order.controller;

import com.seata.order.entity.Order;
import com.seata.order.service.OrderService;
import com.seata.order.tcc.TccHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author LiGezZ
 */
@RestController
@RequestMapping("order")
public class OrderController {


    @Resource
    private TccHandler tccHandler;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        long id = new Random().nextInt(999999999);
        order.setId(id);
        tccHandler.createOrderAndStorage(order);
        return ResponseEntity.status(HttpStatus.OK).body("操作成功");
    }
}
