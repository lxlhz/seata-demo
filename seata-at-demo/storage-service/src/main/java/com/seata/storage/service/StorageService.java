package com.seata.storage.service;

public interface StorageService {

    /**
     * 扣除存储数量
     *
     * @param orderId
     * @param count
     */
    void deduct(Long orderId, int count);
}