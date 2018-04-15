package com.lhc.bookstore.book.domain;

import com.lhc.bookstore.category.domain.Category;

public class Book {
	private String bid;
	private String bname;
	private double price;
	private String author;
	private String image;
	private boolean del;
	private Category category;//所属分类
	public Book(String bid, String bname, double price, String author, String image, Category category) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.price = price;
		this.author = author;
		this.image = image;
		this.category = category;
	}
	public Book() {
		super();
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price + ", author=" + author + ", image=" + image
				+ ", category=" + category + "]";
	}
	
}
