-- 数据库名称： seata-at-demo.sql

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


-- undo_log表
CREATE TABLE `undo_log`
(
    `branch_id`     bigint(20) NOT NULL COMMENT 'branch transaction id',
    `xid`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
    `context`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` longblob                                                NOT NULL COMMENT 'rollback info',
    `log_status`    int(11) NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   datetime(6) NOT NULL COMMENT 'create datetime',
    `log_modified`  datetime(6) NOT NULL COMMENT 'modify datetime',
    UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Compact;