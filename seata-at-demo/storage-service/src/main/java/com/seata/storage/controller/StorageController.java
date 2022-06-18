package com.seata.storage.controller;

import com.seata.storage.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LiGezZ
 */
@RestController
@RequestMapping("storage")
public class StorageController {

    @Resource
    private StorageService storageService;


    /**
     * 扣减库存
     *
     * @param orderId 商品ID
     * @param count   要扣减的数量
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> deduct(@RequestParam("orderId") Long orderId, @RequestParam("count") Integer count) {
        storageService.deduct(orderId, count);
        return ResponseEntity.noContent().build();
    }
}
