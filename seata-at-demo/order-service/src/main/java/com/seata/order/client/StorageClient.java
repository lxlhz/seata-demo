package com.seata.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiGezZ
 */
@FeignClient("storage-service")
public interface StorageClient {
    /**
     * 扣减库存
     *
     * @param orderId
     * @param count
     */
    @PostMapping("/storage")
    void deduct(@RequestParam("orderId") Long orderId, @RequestParam("count") Integer count);
}
