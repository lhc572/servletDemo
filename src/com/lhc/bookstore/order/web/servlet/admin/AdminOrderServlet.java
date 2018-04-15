package com.lhc.bookstore.order.web.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.order.domain.Order;
import com.lhc.bookstore.order.service.OrderService;

import cn.itcast.servlet.BaseServlet;
@WebServlet("/admin/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderService os = new OrderService();
	/**
	 * 所有订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Order> orderList = os.findAll();
		request.setAttribute("orderList", orderList);
		return "/adminjsps/admin/order/list.jsp";
	}
	/**
	 * 根据订单状态查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int state = Integer.parseInt(request.getParameter("state"));
		List<Order> orderList = os.findByState(state);
		request.setAttribute("orderList", orderList);
		return "/adminjsps/admin/order/list.jsp";
	}
	/**
	 * 发货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String send(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		os.send(oid);
		request.setAttribute("msg", "已发货！");
		return "/adminjsps/admin/msg.jsp";
	}

}
