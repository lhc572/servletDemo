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
 * Servlet Filter implementation class AdminLoginFilter
 */
@WebFilter({ "/admin/*", "/adminjsps/admin/*" })
public class AdminLoginFilter implements Filter {

	public void destroy() {}
	public void init(FilterConfig fConfig) throws ServletException {}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String admin =(String)req.getSession().getAttribute("admin");
		if(admin != null && !admin.trim().isEmpty()) {
			chain.doFilter(request, response);
		}else {
			//req.setAttribute("msg", "您还没有登录！");
			req.getRequestDispatcher("/adminjsps/login.jsp")
					.forward(req, response);
		}
		
	}

	

}
