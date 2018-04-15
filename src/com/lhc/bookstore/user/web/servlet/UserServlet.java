package com.lhc.bookstore.user.web.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.cart.domain.Cart;
import com.lhc.bookstore.user.domain.User;
import com.lhc.bookstore.user.service.UserException;
import com.lhc.bookstore.user.service.UserService;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//封装表单数据
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		//表单校验
		Map<String, String> errors = new HashMap<String, String>();
		
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空！");
		}else if(username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度为3~10");
		}
		String password = form.getPassword();
		if(password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空！");
		}else if(password.length() < 3 || password.length() > 10) {
			errors.put("password", "密码长度为3~10");
		}
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "邮箱不能为空！");
		}else if(!email.matches("\\w+@\\w+\\.com")) {
			errors.put("email", "邮箱格式错误！");
		}
		if(errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("user", form);
			return "/jsps/user/regist.jsp";
		}
		
		//进入service层
		try {
			userService.regist(form);
		} catch (UserException e) {
			//若抛出异常说明用户名或邮箱已被注册
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", form);
			return "/jsps/user/regist.jsp";
		}
		
		//没有抛出异常，则验证邮箱！
		Properties prop = new Properties();
		prop.load(this.getClass().getClassLoader()
				.getResourceAsStream("email_template.properties"));
		String host = prop.getProperty("host");
		String uname = prop.getProperty("uname");
		String pwd = prop.getProperty("pwd");
		String from = prop.getProperty("from");
		String to = form.getEmail();
		String subject = prop.getProperty("subject");
		String content = prop.getProperty("content");
		//激活码用于账号激活
		content = MessageFormat.format(content, form.getCode());
		
		Session session = MailUtils.createSession(host, uname, pwd);
		//创建邮箱对象
		Mail mail = new Mail(from, to, subject, content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		request.setAttribute("msg", "账号激活信息已发送到您的邮箱，成功激活后才能正常登录！");
		return "/jsps/msg.jsp";
	}

	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			userService.active(code);
			request.setAttribute("msg", "激活成功！您可以正常登录了！");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "/jsps/msg.jsp";
	}
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			User user = userService.login(form);
			request.getSession().setAttribute("user", user);
			//登录成功创建购物车对象
			request.getSession().setAttribute("cart", new Cart());
			return "r:/index.jsp";
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", form);
			return "/jsps/user/login.jsp";
		}
	}
	public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
	}
	/**
	 * 管理员登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("adminname");
		String password = request.getParameter("password");
		if(username.equals("刘洹成") && password.equals("572521")) {
			request.getSession().setAttribute("admin", "刘洹成");
			return "/adminjsps/admin/index.jsp";
		}else {
			request.setAttribute("msg", "您没有权限访问！");
			return "f:/adminjsps/msg.jsp";
		}
		
	}
	
}
