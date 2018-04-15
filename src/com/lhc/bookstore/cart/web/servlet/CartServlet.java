package com.lhc.bookstore.cart.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.book.domain.Book;
import com.lhc.bookstore.book.service.BookService;
import com.lhc.bookstore.cart.domain.Cart;
import com.lhc.bookstore.cart.domain.CartItem;

import cn.itcast.servlet.BaseServlet;
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 购物车只有用户登录，才创建
		 */
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		int count = Integer.parseInt(request.getParameter("count"));
		Book book = new BookService().load(bid);
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		cart.add(cartItem);
		return "/jsps/cart/list.jsp";
	}
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.clear();
		return "/jsps/cart/list.jsp";
	}
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.delete(bid);
		return "/jsps/cart/list.jsp";
	}
	public String getCartItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.getCartItems();
		return "/jsps/cart/list.jsp";
	}

}
