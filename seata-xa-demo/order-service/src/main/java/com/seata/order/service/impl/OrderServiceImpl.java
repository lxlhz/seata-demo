package com.seata.order.service.impl;

import com.seata.order.client.StorageClient;
import com.seata.order.entity.Order;
import com.seata.order.mapper.OrderMapper;
import com.seata.order.service.OrderService;
import feign.FeignException;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author LiGezZ
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StorageClient storageClient;

    @Resource
    private OrderMapper orderMapper;


    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @Override
    @GlobalTransactional
    public Long create(Order order) {
        // 创建订单
        long id = new Random().nextInt(999999999);
        order.setId(id);
        orderMapper.insert(order);
        try {
            // 记录库存信息
            storageClient.deduct(order.getId(), order.getCount());
            // 模拟上游异常
            // int a = 1 / 0;
        } catch (FeignException e) {
            log.error("下单失败，原因:{}", e.contentUTF8(), e);
            throw new RuntimeException(e.contentUTF8(), e);
        }
        return order.getId();
    }

}
