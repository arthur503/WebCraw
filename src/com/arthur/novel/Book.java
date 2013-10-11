package com.arthur.novel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Book {

	private Category category;
	private String name;
	private String uri;
	private String author;
	private String bookId;
	private LinkedList<Chapter> chapters;
	
	public Book(){
		this.chapters = new LinkedList<Chapter>();
	}
	
	public Book(String name){
		this.name = name;
		this.chapters = new LinkedList<Chapter>();
	}
	
	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}
	
	public void addChapter(Chapter chapter){
		chapter.setBook(this);
		chapters.add(chapter);
	}
	
	public Chapter getChapter(int index){
		return chapters.get(index);
	}
	
	public LinkedList<Chapter> getChapters(){
		return chapters;
	}
	
/*	public void setUri(String uri){
		this.uri = uri;
	}
	
	public String getUri(){
		return uri;
	}*/
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setId(String id){
		this.bookId = id;
	}
	
	public String getId(){
		return bookId;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public String getAuthor(){
		return author;
	}
	


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("Book Name:"+name+"\n");
		sb.append("Book Author:"+author+"\n");
		sb.append("Book Id:"+bookId+"\n");
		for(int i=0;i<chapters.size();i++){
			sb.append(chapters.get(i)+"\n");
		}
		return sb.toString();
	}

	
}
