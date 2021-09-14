package com.cloud.zhao.seata.storage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.zhao.seata.storage.mapper.StorageMapper;
import com.cloud.zhao.seata.storage.service.StorageService;


@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Autowired
    private StorageMapper storageMapper;

    /**
     * 扣减库存
     */
    @Override
    public void decrease(Long productId, Integer count) {
        LOGGER.info("------->storage-service中扣减库存开始");
        storageMapper.decrease(productId,count);
        LOGGER.info("------->storage-service中扣减库存结束");
    }
}
