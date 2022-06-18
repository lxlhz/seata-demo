package com.seata.order.service;

import com.seata.order.entity.Order;

/**
 * @author LiGezZ
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    Long create(Order order);
}