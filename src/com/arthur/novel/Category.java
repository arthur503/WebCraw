package com.arthur.novel;

import java.util.LinkedList;

public class Category {
	
	private Site site;
	private String name;
	private String uri;
	private LinkedList<Book> books;
	
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
	
/*	public void setUri(String uri){
		this.uri = uri;
	}
	
	public String getUri(){
		return uri;
	}*/
}
