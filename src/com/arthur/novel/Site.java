package com.arthur.novel;

import java.util.LinkedList;

public class Site {

	public static final String BASE_URI = "D:\\data\\webcraw";
	private String name;
	private String DEFAULT_NAME = "SITE";
	private String url;
	private LinkedList<Category> categories;
	
	public Site(){
		this.name = DEFAULT_NAME;
		this.categories = new LinkedList<Category>();
	}
	
	public Site(String name){
		this.name = name;
		this.categories = new LinkedList<Category>();
	}
	
	public Category newCategory(){
		//设置category中的site参数。
		Category category = new Category();
		addCategory(category);
		return category;
	}
	
	public void addCategory(Category category){
		//设置category中的site参数。
		category.setSite(this);
		categories.add(category);
	}
	
	public void getCategory(int index){
		categories.get(index);
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

	public String getSiteBaseUri(){
		return BASE_URI;
	}
}
