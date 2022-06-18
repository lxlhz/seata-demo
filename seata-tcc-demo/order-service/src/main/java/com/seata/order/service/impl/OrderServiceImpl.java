package com.seata.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.seata.order.client.StorageClient;
import com.seata.order.entity.Order;
import com.seata.order.mapper.OrderMapper;
import com.seata.order.service.OrderService;
import feign.FeignException;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
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
    private OrderMapper orderMapper;


    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @Override
    public Order createOrderPrepare(Order order) {
        // 0.获取事务id
        String xid = RootContext.getXID();
        log.info("创建订单预处理，xid={}",xid );

        // 设置为预处理状态
        order.setStatus(1);
        orderMapper.insert(order);
        return order;
    }

    /**
     * 提交
     * @param context
     * @return
     */
    @Override
    public  Boolean createOrderCommit(BusinessActionContext context){
        try {
            String xid = context.getXid();
            // 将订单的状态修改为完成
            log.info("创建订单提交处理，xid={}",xid );
            Object obj = context.getActionContext("order");
            if(obj!=null) {
                Order order = JSON.parseObject(obj.toString(), Order.class);
                if (order != null) {
                    order.setStatus(2);
                    orderMapper.updateById(order);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return true;
    }

    /**
     * 回滚
     * @param context
     * @return
     */
    @Override
    public  Boolean createOrderRollBack(BusinessActionContext context){
        try {
            String xid = context.getXid();
            log.info("创建订单回滚处理，xid={}",xid );

            // 将订单的状态修改为完成
            Object obj = context.getActionContext("order");
            if(obj!=null) {
                Order order = JSON.parseObject(obj.toString(), Order.class);
                // 将订单进行删除，表示回滚
                if (order != null) {
                    log.info("删除订单ID:"+order.getId());
                    orderMapper.deleteById(order.getId());
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return true;
    }
}
