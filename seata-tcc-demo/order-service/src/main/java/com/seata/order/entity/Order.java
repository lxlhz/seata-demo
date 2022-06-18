package com.seata.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author LiGezZ
 */
@Data
@TableName("tb_order")
public class Order {
    /**
     * 主键
     */
    private Long id;
    /**
     * 数量
     */
    private Integer count;

    /**
     * 金额(整数)
     */
    private Integer money;

    /**
     * 状态：1：预处理，2-完成
     */
    private Integer status;
}
