package com.lhc.bookstore.order.domain;

import com.lhc.bookstore.book.domain.Book;

/**
 * 订单条目
 * @author lhc
 *
 */
public class OrderItem {

	private	String iid;
	private int count;
	private double subtotal;
	//javaBean与关系型数据库表之间的映射，以下两个是外键
	private Order order;
	private Book book;
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "OrderItem [iid=" + iid + ", count=" + count + ", subtotal=" + subtotal + ", book=" + book + "]";
	}
	
	
}
