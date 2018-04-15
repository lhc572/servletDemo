package com.lhc.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lhc.bookstore.book.domain.Book;
import com.lhc.bookstore.category.domain.Category;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();

	public List<Book> findAll() {
		String sql ="SELECT * FROM book WHERE del = false";
		
		try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Book> findByCategory(String cid) {
		String sql = "SELECT * FROM book WHERE cid = ? AND del = false";
		try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 返回一个带有category的Book对象
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		String sql = "SELECT * FROM book WHERE bid = ? AND del = false";
		try {
			//用map 
			Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
			Book book = CommonUtils.toBean(map, Book.class);
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 假删除(del == true)
	 * @param bid
	 */
	public void delete(String bid) {
		String sql = "UPDATE book SET del = true WHERE bid = ?";
		try {
			qr.update(sql, bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void edit(Book book) {
		String sql = "UPDATE book SET bname = ?, price = ?, author = ?, image = ?, cid = ? where bid = ?";
		Object[] params = {book.getBname(), book.getPrice(), book.getAuthor()
				, book.getImage(), book.getCategory().getCid(), book.getBid()}; 
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int findByCid(String cid) {
		String sql = "SELECT COUNT(*) FROM book WHERE cid = ? AND del = false";
		try {
			Number count = (Number)qr.query(sql, new ScalarHandler(), cid);
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Book book) {
		String sql = "INSERT INTO book VALUES(?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(), book.getBname(), book.getPrice(),
				book.getAuthor(), book.getImage(), book.getCategory().getCid(), 0};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
