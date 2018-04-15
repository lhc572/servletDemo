package com.lhc.bookstore.book.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.book.domain.Book;
import com.lhc.bookstore.book.service.BookService;

import cn.itcast.servlet.BaseServlet;

@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bs = new BookService();
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> bookList = bs.findAll();
		request.setAttribute("bookList", bookList);
		return "f:/jsps/book/list.jsp";
	}
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		List<Book> bookList = bs.findByCategory(cid);
		request.setAttribute("bookList", bookList);
		return "f:/jsps/book/list.jsp";
	}
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Book book = bs.load(bid);
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}

}
