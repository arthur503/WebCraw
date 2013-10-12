package com.arthur.novel;

import java.util.LinkedList;

public class Category {
	
	private Site site = new Site();
	private String name;
	private String DEFAULT_NAME = "CATEGORY";
	private String url;
	private LinkedList<Book> books;
	
	public Category(){
		this.name = DEFAULT_NAME;
		this.books = new LinkedList<Book>();
	}
	
	public Category(String name){
		this.name = name;
		this.books = new LinkedList<Book>();
	}
	
	public void setSite(Site site){
		this.site = site;
//		this.uri = site.getUri() + "\\" + this.name;
	}
	
	public Site getSite(){
		return site;
	}

	public Book newBook(){
		Book book = new Book();
		addBook(book);
		return book;
	}
	
	public void addBook(Book book){
		//设置book中的Category参数。
		book.setCategory(this);
		books.add(book);
	}
	
	public void getCategory(int index){
		books.get(index);
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return url;
	}
}
