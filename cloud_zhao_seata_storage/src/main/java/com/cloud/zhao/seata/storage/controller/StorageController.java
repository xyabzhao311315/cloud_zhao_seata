package com.cloud.zhao.seata.storage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.zhao.seata.storage.domain.CommonResult;
import com.cloud.zhao.seata.storage.service.StorageService;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     */
    @RequestMapping("/decrease")
    public CommonResult<?> decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
        return new CommonResult<>("扣减库存成功！",200);
    }
}
