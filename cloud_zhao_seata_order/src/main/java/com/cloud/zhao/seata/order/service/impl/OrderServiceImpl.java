package com.cloud.zhao.seata.order.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.zhao.seata.order.domain.Order;
import com.cloud.zhao.seata.order.mapper.OrderMapper;
import com.cloud.zhao.seata.order.service.AccountService;
import com.cloud.zhao.seata.order.service.OrderService;
import com.cloud.zhao.seata.order.service.StorageService;

import io.seata.spring.annotation.GlobalTransactional;

/**
 * 订单业务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Resource
	private OrderMapper orderMapper;
	@Autowired
	private StorageService storageService;
	@Autowired
	private AccountService accountService;

	/**
	 * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
	 */
	@Override
	@GlobalTransactional(rollbackFor = Exception.class, timeoutMills = 10000000)
	public void create(Order order) {
		order.setCount(1);
		order.setUserId(1L);
		order.setStatus(0);
		order.setMoney(new BigDecimal(1));
		order.setProductId(1L);
		LOGGER.info("------->下单开始");
		// 本应用创建订单
		orderMapper.create(order);

		// 远程调用库存服务扣减库存
		LOGGER.info("------->order-service中扣减库存开始");
		storageService.decrease(order.getProductId(), order.getCount());
		LOGGER.info("------->order-service中扣减库存结束");

		// 远程调用账户服务扣减余额
		LOGGER.info("------->order-service中扣减余额开始");
		accountService.decrease(order.getUserId(), order.getMoney());
		LOGGER.info("------->order-service中扣减余额结束");

		// 修改订单状态为已完成
		LOGGER.info("------->order-service中修改订单状态开始");
		orderMapper.update(order.getUserId(), 0);
		LOGGER.info("------->order-service中修改订单状态结束");
		
		LOGGER.info("------->下单结束");
		throw new RuntimeException("kfsdahfksd");
	}
}
