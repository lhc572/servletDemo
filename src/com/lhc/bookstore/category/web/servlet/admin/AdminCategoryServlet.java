package com.lhc.bookstore.category.web.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.category.domain.Category;
import com.lhc.bookstore.category.service.CategoryService;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
@WebServlet("/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService cs = new CategoryService();
	/**
	 * 查看分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = cs.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/category/list.jsp";
	}
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		try {
			cs.add(category);
			return findAll(request, response);
		} catch (CategoryException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("category", category);
			return "/adminjsps/admin/category/add.jsp";
		}
	}
	/**
	 * 删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		try {
			cs.delete(cid);
			return findAll(request, response);
		} catch (CategoryException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/adminjsps/msg.jsp";
		}
	}
	/**
	 * 根据id返回单个对象
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Category category = cs.load(cid);
		request.setAttribute("category", category);
		return "/adminjsps/admin/category/mod.jsp";
	}
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		cs.edit(category);
		return findAll(request, response);
	}

}
