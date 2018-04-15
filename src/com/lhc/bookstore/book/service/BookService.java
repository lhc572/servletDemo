package com.lhc.bookstore.book.service;

import java.util.List;

import com.lhc.bookstore.book.dao.BookDao;
import com.lhc.bookstore.book.domain.Book;

public class BookService {
	private BookDao bd = new BookDao();

	public List<Book> findAll() {
		return bd.findAll();
	}

	public List<Book> findByCategory(String cid) {
		return bd.findByCategory(cid);
	}
	
	public Book load(String bid) {
		return bd.load(bid);
	}

	public void delete(String bid) {
		bd.delete(bid);
		
	}

	public void edit(Book book) {
		bd.edit(book);
		
	}

	public void add(Book book) {
		bd.add(book);
		
	}
}
