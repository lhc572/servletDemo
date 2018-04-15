package com.lhc.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lhc.bookstore.book.domain.Book;
import com.lhc.bookstore.order.domain.Order;
import com.lhc.bookstore.order.domain.OrderItem;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {

	private QueryRunner qr = new TxQueryRunner();

	public void addOrder(Order order) {
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?)";
		Timestamp time = new Timestamp(order.getOrdertime().getTime());
		Object[] params = {order.getOid(), time, order.getTotal()
				,order.getState(), order.getOwner().getUid(), order.getAddress()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入订单条目  批处理
	 * params为二维数组即多个一维数组: Object[][]
	 * 每个一维数组都执行一次sql模板
	 * @param orderItemList
	 */
	public void addOrderItem(List<OrderItem> orderItemList) {
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		Object[][] params = new Object[orderItemList.size()][];
		//遍历为每个一维数组赋值
		for(int i = 0; i < orderItemList.size(); i++) {
			OrderItem item = orderItemList.get(i);
			params[i] = new Object[]{item.getIid(), item.getCount(), item.getSubtotal()
					, item.getOrder().getOid(), item.getBook().getBid()};
		}
		try {
			qr.batch(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 查找用户的所有订单
	 * @param uid
	 * @return
	 */
	public List<Order> findByUid(String uid) {
		String sql = "SELECT * FROM orders WHERE uid = ?";
		try {
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
			for(Order order : orderList) {
				//获取每个订单的订单条目
				List<OrderItem> orderItemList = getOrderItemList(order);
				order.setOrderItemList(orderItemList);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取每个订单的订单条目
	 * 一个订单对应多个订单条目
	 * 一个订单条目应包含书的信息
	 * @param order
	 * @return
	 */
	private List<OrderItem> getOrderItemList(Order order) {
		String sql = "SELECT * FROM orderitem o, book b WHERE oid = ? and o.bid = b.bid";
		try {
			//多表查询用map 
			List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
			List<OrderItem> orderItemList = new ArrayList<OrderItem>();
			for(Map<String, Object> map : mapList) {
				//将查询出来的一条map记录，转换成一条订单条目
				OrderItem orderItem = getOrderItem(map);
				orderItemList.add(orderItem);
			}
			return orderItemList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * map转换成OrderItem
	 * @param map
	 * @return
	 */
	private OrderItem getOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

	public Order load(String oid) {
		String sql = "SELECT * FROM orders WHERE oid = ?";
		try {
			Order order = (Order) qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			List<OrderItem> orderItemList = getOrderItemList(order);
			order.setOrderItemList(orderItemList);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int getStateByOid(String oid) {
		String sql = "SELECT state FROM orders WHERE oid = ?";
		try {
			int state = (Integer)qr.query(sql, new ScalarHandler(), oid);
			return state;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateState(String oid, int state) {
		String sql = "UPDATE orders SET state = ? WHERE oid = ?";
		Object[] params = {state, oid};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public List<Order> findAll() {
		String sql = "SELECT * FROM orders";
		try {
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class));
			for(Order order : orderList) {
				List<OrderItem> orderItemList = getOrderItemList(order);
				order.setOrderItemList(orderItemList);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据订单状态返回订单列表
	 * @param state
	 * @return
	 */
	public List<Order> findByState(int state) {
		String sql = "SELECT * FROM orders WHERE state = ?";
		try {
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), state);
			for(Order order : orderList) {
				//获取每个订单的订单条目
				List<OrderItem> orderItemList = getOrderItemList(order);
				order.setOrderItemList(orderItemList);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

