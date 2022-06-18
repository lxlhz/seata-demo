package com.seata.order.mapper;

import com.seata.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LiGezZ
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
