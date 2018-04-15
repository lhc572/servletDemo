package com.lhc.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lhc.bookstore.category.domain.Category;

import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	
	private QueryRunner qr = new TxQueryRunner();

	public List<Category> findAll() {
		String sql = "SELECT * FROM category";
		try {
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int findByCname(String cname) {
		String sql = "SELECT COUNT(*) FROM category WHERE cname = ?";
		try {
			Number count = (Number)qr.query(sql, new ScalarHandler(), cname);
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Category category) {
		String sql = "INSERT INTO category VALUES(?,?)";
		try {
			qr.update(sql, category.getCid(), category.getCname());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void delete(String cid) {
		String sql = "DELETE FROM category WHERE cid = ?";
		try {
			qr.update(sql, cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public Category load(String cid) {
		String sql = "SELECT * FROM category WHERE cid = ?";
		try {
			Category category = qr.query(sql, new BeanHandler<Category>(Category.class), cid);
			return category;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void edit(Category category) {
		String sql = "UPDATE category SET cname = ? WHERE cid = ?";
		try {
			qr.update(sql, category.getCname(), category.getCid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
