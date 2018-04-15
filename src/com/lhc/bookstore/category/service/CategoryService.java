package com.lhc.bookstore.category.service;

import java.util.List;

import com.lhc.bookstore.book.dao.BookDao;
import com.lhc.bookstore.category.dao.CategoryDao;
import com.lhc.bookstore.category.domain.Category;
import com.lhc.bookstore.category.web.servlet.admin.CategoryException;

public class CategoryService {
	
	private CategoryDao cd = new CategoryDao();
	private BookDao bd = new BookDao();

	public List<Category> findAll() {
		return cd.findAll();
	}

	public void add(Category category) throws CategoryException {
		int count = cd.findByCname(category.getCname());
		if(count > 0) {
			throw new CategoryException("分类名已存在！");
		}
		cd.add(category);
		
	}

	/**
	 * 若分类下还有图书，不能删除
	 * @param cid
	 * @throws CategoryException 
	 */
	public void delete(String cid) throws CategoryException {
		int count = bd.findByCid(cid);
		if(count > 0) {
			throw new CategoryException("删除失败，分类下还有图书！");
		}
		cd.delete(cid);
		
	}

	public Category load(String cid) {
		
		return cd.load(cid);
	}

	public void edit(Category category) {
		cd.edit(category);
		
	}
}
