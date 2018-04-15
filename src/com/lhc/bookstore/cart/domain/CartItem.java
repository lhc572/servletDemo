package com.lhc.bookstore.cart.domain;

import java.math.BigDecimal;

import com.lhc.bookstore.book.domain.Book;

/**
 * 购物车商品条目
 * 此购物车是session级别的，不保存于数据库！
 * @author lhc
 *
 */
public class CartItem {
	private Book book;
	private int count;
	/**
	 * 商品条目价格小计
	 * BigDecimal处理二进制误差问题
	 * @return
	 */
	public double getSubtotal() {
		BigDecimal bd1 = new BigDecimal(book.getPrice() + "");
		BigDecimal bd2 = new BigDecimal(count + "");
		return bd1.multiply(bd2).doubleValue();
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
