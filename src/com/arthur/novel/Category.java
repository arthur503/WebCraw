package com.arthur.novel;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.arthur.bloomfilter.BloomFilter;
import com.arthur.main.Main;

public class Category {
	
	private Site site = new Site();
	private String name;
	private String DEFAULT_NAME = "CATEGORY";
	private String url;
	private LinkedList<Book> bookList;
	public static Logger logger = Logger.getLogger(Main.class.getName());
	private BloomFilter categoryBloomFilter = new BloomFilter(10000);	
	
	public Category(){
		this.name = DEFAULT_NAME;
		this.bookList = new LinkedList<Book>();
	}
	
	public Category(String name){
		this.name = name;
		this.bookList = new LinkedList<Book>();
	}
	
	public void setSite(Site site){
		this.site = site;
//		this.uri = site.getUri() + "\\" + this.name;
	}
	
	public Site getSite(){
		return site;
	}
/*
	public Book newBook(){
		Book book = new Book();
		addBook(book);
		return book;
	}*/
	
	public boolean addBook(String bookUrl){
		//设置book中的Category参数。
		if(!categoryBloomFilter.checkAndAdd(bookUrl)){
			logger.debug("Chapter ["+bookUrl+"] have been added before!");
			return false;
		}
		Book book = new Book();
		book.setUrl(bookUrl);
//		addChapter(chapter);
		book.setCategory(this);
		bookList.add(book);
		return true;
	}
	
	private void addBook(Book book){
		//设置book中的Category参数。
		book.setCategory(this);
		bookList.add(book);
	}
	
	public void getCategory(int index){
		bookList.get(index);
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
	
	public LinkedList<Book> getBookList(){
		return bookList;
	}
	
	public Book getBook(int i){
		return bookList.get(i);
	}
}
