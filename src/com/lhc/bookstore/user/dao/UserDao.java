package com.lhc.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.lhc.bookstore.user.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();

	public User queryByUsername(String username) {
		String sql = "SELECT * FROM tb_user WHERE username = ?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User queryByEmail(String email) {
		String sql = "SELECT * FROM tb_user WHERE email = ?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void regist(User form) {
		String sql = "INSERT INTO tb_user(uid, username, password, email, code, state) "
				+ "VALUES(?,?,?,?,?,?)";
		Object[] params  = {form.getUid(), form.getUsername(), form.getPassword(), 
				form.getEmail(), form.getCode(), form.isState()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User queryByCode(String code) {
		String sql = "SELECT * FROM tb_user WHERE code = ?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateState(String uid, boolean b) {
		String sql = "UPDATE tb_user SET state = ? WHERE uid = ?";
		Object[] params = {b, uid}; 
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
