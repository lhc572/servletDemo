package com.lhc.bookstore.book.web.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.book.domain.Book;
import com.lhc.bookstore.book.service.BookService;
import com.lhc.bookstore.category.domain.Category;
import com.lhc.bookstore.category.service.CategoryService;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bs = new BookService();
	private CategoryService cs = new CategoryService();
	/**
	 * 查看图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> bookList = bs.findAll();
		request.setAttribute("bookList", bookList);
		return "/adminjsps/admin/book/list.jsp";
	}
	/**
	 * 根据id返回一个对象(带有Category属性)
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Book book = bs.load(bid);
		request.setAttribute("book", book);
		//需要返回图书分类列表
		List<Category> categoryList = cs.findAll();
		request.setAttribute("categoryList", categoryList);
		return "/adminjsps/admin/book/desc.jsp";
	}
	/**
	 * 删除图书(假删除)
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bid = request.getParameter("bid");
		bs.delete(bid);
		return findAll(request, response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		book.setCategory(category);
		bs.edit(book);
		return findAll(request, response);
	}
	/**
	 * 加载图书分类信息到add.jsp
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = cs.findAll();
		request.setAttribute("categoryList", categoryList);
		return "/adminjsps/admin/book/add.jsp";
	}

}
