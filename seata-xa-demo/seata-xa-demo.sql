-- 数据库名称： seata-xa-demo.sql

-- 订单表
CREATE TABLE `tb_order`
(
    `id`    int(11) NOT NULL COMMENT '主键',
    `count` int(11) NULL DEFAULT 0 COMMENT '下单数量',
    `money` int(11) NULL DEFAULT 0 COMMENT '金额',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;


-- 库存表
CREATE TABLE `tb_storage`
(
    `id`       int(11) NOT NULL COMMENT '主键',
    `order_id` int(11) NOT NULL COMMENT '订单ID',
    `count`    int(11) NOT NULL DEFAULT 0 COMMENT '库存',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;
