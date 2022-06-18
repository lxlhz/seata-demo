package com.seata.order.tcc;

import com.seata.order.client.StorageClient;
import com.seata.order.entity.Order;
import com.seata.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: LiHuaZhi
 * @Date: 2022/6/18 23:10
 * @Description:
 **/
@Component
@Slf4j
public class TccHandler {

    @Resource
    private OrderService orderService;

    @Resource
    private StorageClient storageClient;

    /**
     * 创建订单和库存记录的TCC处理器
     * 使用@GlobalTransactional开启全局事务
     * @param order
     * @return
     */
    @GlobalTransactional
    public void createOrderAndStorage(Order order) {

        // 记录订单数据
        log.info("开始记录订单数据...");
        Order orderPrepare = orderService.createOrderPrepare(order);
        log.info("结束记录订单数据...");

        // feign调用记录库存数据
        log.info("开始记录库存数据...");
        storageClient.deduct(orderPrepare.getId(),orderPrepare.getCount());
        log.info("结束记录库存数据...");

        // 模拟最后出现异常情况
        int a=1/0;
    }
}
