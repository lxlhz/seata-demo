package com.seata.storage.service.impl;

import com.alibaba.fastjson.JSON;
import com.seata.storage.entity.Storage;
import com.seata.storage.mapper.StorageMapper;
import com.seata.storage.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LiGezZ
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    private static final Map<String, String> STATUS_MAP = new ConcurrentHashMap<>();

    @Resource
    private StorageMapper storageMapper;

    /**
     * 扣除存储数量
     *
     */
    @Override
    public void deductPrepare( Storage storage) {
        // 0.获取事务id
        String xid = RootContext.getXID();
        log.info("记录库存信息预处理，xid={}",xid );
        try {
            // 设置为预处理状态
            storage.setStatus(1);

            // 判断是否已经执行过了Cancel或者Confirm
            if(STATUS_MAP.get(xid)!=null){
                // 表示已经执行了Cancel或者Confirm实现业务悬挂
                return ;
            }

            storageMapper.insert(storage);

            // 下游服务抛出异常
            // int a = 1 / 0;
        } catch (Exception e) {
            throw new RuntimeException("扣减库存失败，可能是库存不足！", e);
        }
        log.info("库存信息记录成功");
    }

    /**
     * 提交
     * @param context
     * @return
     */
    @Override
    public  Boolean deductCommit(BusinessActionContext context){
        try {
            String xid = context.getXid();
            // 将状态修改为完成
            log.info("记录库存信息提交处理，xid={}", xid);
            // 幂等处理
            if(STATUS_MAP.get(xid)!=null){
                return true;
            }

            STATUS_MAP.put(xid,"Confirm");

            Object obj = context.getActionContext("storage");
            if (obj != null) {
                Storage storage = JSON.parseObject(obj.toString(), Storage.class);
                if (storage != null) {
                    storage.setStatus(2);
                    storageMapper.updateById(storage);
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
    public  Boolean deductRollBack(BusinessActionContext context){
        try {
            String xid = context.getXid();
            log.info("记录库存信息回滚处理，xid={}",xid );

            // 幂等处理
            if(STATUS_MAP.get(xid)!=null){
                return true;
            }
            STATUS_MAP.put(xid,"Cancel");

            // 将订单的状态修改为完成
            Object obj = context.getActionContext("storage");
            if(obj!=null) {
                Storage storage = JSON.parseObject(obj.toString(), Storage.class);
                if (storage != null) {
                    // 将记录进行删除，表示回滚
                    log.info("删除记录ID:"+storage.getId());
                    storageMapper.deleteById(storage.getId());
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return true;
    }
}
