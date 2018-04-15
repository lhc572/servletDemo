package com.lhc.bookstore.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhc.bookstore.category.domain.Category;
import com.lhc.bookstore.category.service.CategoryService;

import cn.itcast.servlet.BaseServlet;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService cs = new CategoryService();
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = cs.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/jsps/left.jsp";
	}

}
