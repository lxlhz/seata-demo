package com.seata.order.service;

import com.seata.order.entity.Order;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author LiGezZ
 */
// 开启本地TCC
@LocalTCC
public interface OrderService {

    /**
     * 创建订单
     * @TwoPhaseBusinessAction 描述⼆阶段提交
     * name: 为 tcc⽅法的 bean 名称，需要全局唯⼀，⼀般写⽅法名即可
     * commitMethod: Commit⽅法的⽅法名
     * rollbackMethod:Rollback⽅法的⽅法名
     * @BusinessActionContextParamete 该注解⽤来修饰 Try⽅法的⼊参，
     * 被修饰的⼊参可以在 Commit ⽅法和 Rollback ⽅法中通过BusinessActionContext 获取。
     * @param order
     * @return
     */
    @TwoPhaseBusinessAction(name = "createOrderPrepare", commitMethod = "createOrderCommit", rollbackMethod = "createOrderRollBack")
    Order createOrderPrepare(@BusinessActionContextParameter(paramName = "order") Order order);


    /**
     * 提交
     * @param context
     * @return
     */
    Boolean createOrderCommit(BusinessActionContext context);

    /**
     * 回滚
     * @param context
     * @return
     */
    Boolean createOrderRollBack(BusinessActionContext context);
}