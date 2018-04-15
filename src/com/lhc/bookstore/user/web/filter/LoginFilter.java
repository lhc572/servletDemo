package com.lhc.bookstore.user.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.user.domain.User;

/**
 * 登录验证过滤器
 */
@WebFilter({ "/jsps/cart/*", "/jsps/order/*", "/CartServlet", "/OrderServlet" })
public class LoginFilter implements Filter {
	public void destroy() {}
	public void init(FilterConfig fConfig) throws ServletException {}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		User user = (User)req.getSession().getAttribute("user");
		if(user != null ) {
			chain.doFilter(request, response);
		}else {
			req.getRequestDispatcher("/jsps/user/login.jsp")
					.forward(req, response);
		}
		
	}
}
