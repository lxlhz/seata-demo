package com.seata.storage.mapper;

import com.seata.storage.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author LiGezZ
 */
@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
}
