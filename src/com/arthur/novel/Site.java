package com.arthur.novel;

import java.util.LinkedList;

public class Site {

	public static final String BASE_URI = "D:\\data\\webcraw";
	private String name;
	private String uri;
	private LinkedList<Category> categories;
	
	public Site(String name){
		this.name = name;
		this.categories = new LinkedList<Category>();
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
	
/*	public void setUri(String uri){
		this.uri = uri;
	}
	*/
	public String getSiteBaseUri(){
		return BASE_URI;
	}
}
