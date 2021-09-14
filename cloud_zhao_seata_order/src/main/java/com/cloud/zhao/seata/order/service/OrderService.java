package com.cloud.zhao.seata.order.service;


import com.cloud.zhao.seata.order.domain.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);
}
