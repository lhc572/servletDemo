package com.lhc.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import com.lhc.bookstore.order.dao.OrderDao;
import com.lhc.bookstore.order.domain.Order;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {

	private OrderDao od = new OrderDao();

	/**
	 * Order和OrderItem应在同一个事务下进行
	 * @param order
	 */
	public void add(Order order) {
		
		try {
			//开启事务
			JdbcUtils.beginTransaction();
			od.addOrder(order);
			od.addOrderItem(order.getOrderItemList());
			//提交事务
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 我的订单
	 * @param uid
	 * @return
	 */
	public List<Order> myOrders(String uid) {
		return od.findByUid(uid);
	}

	public Order load(String oid) {
		return od.load(oid);
	}

	public void confirm(String oid) throws OrderException {
		int state = od.getStateByOid(oid);
		if(state != 3) {
			throw new OrderException("订单状态异常！");
		}
		od.updateState(oid, 4);
	}

	public void zhiFu(String oid) {
		int state = od.getStateByOid(oid);
		//订单状态是未付款状态
		if(state == 1) {
			od.updateState(oid, 2);
		}
	}

	/**
	 * 查看所有订单
	 * @return 
	 */
	public List<Order> findAll() {
		return od.findAll();
		
	}

	public List<Order> findByState(int state) {
		return od.findByState(state);
	}

	public void send(String oid) {
		od.updateState(oid, 3);
		
	}
}
