package com.lhc.bookstore.user.service;

import com.lhc.bookstore.user.dao.UserDao;
import com.lhc.bookstore.user.domain.User;
/**
 * 业务层
 * @author lhc
 *
 */
public class UserService {
	
	private UserDao userDao = new UserDao();
	public void regist(User form) throws UserException {
		
		//校验用户名是否注册，若已注册抛异常
		User user = userDao.queryByUsername(form.getUsername());
		if(user != null) {
			throw new UserException("用户名已注册！");
		}
		//校验是否注册，若已注册抛异常
		user = userDao.queryByEmail(form.getEmail());
		if(user != null) {
			throw new UserException("邮箱已注册！");
		}
		userDao.regist(form);
	}
	/**
	 * 邮箱激活
	 * @param code
	 * @throws UserException 
	 */
	public void active(String code) throws UserException {
		User user = userDao.queryByCode(code);	
		if(user == null) {
			throw new UserException("激活码无效！");
		}
		if(user.isState()) {
			throw new UserException("该账号已经被激活！不可重复激活！");
		}
		userDao.updateState(user.getUid(), true);
	}
	public User login(User form) throws UserException {
		User user = userDao.queryByUsername(form.getUsername());
		if(user == null) {
			throw new UserException("用户名不存在！");
		}
		if(!user.getPassword().equals(form.getPassword())) {
			throw new UserException("密码错误！");
		}
		if(!user.isState()) {
			throw new UserException("尚未激活！");
		}
		return user;
	}
}
