package com.arthur.novel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.arthur.bloomfilter.BloomFilter;

public class Book {

	private Category category = new Category();
	private String name;
	private String DEFAULT_NAME = "BOOK";
	private String url;
	private String author;
	private String bookId;
	private LinkedList<Chapter> chapters;
	
	//【待补充】利用bloomfilter对chapter的url进行查重；
	private BloomFilter chapterBloomFilter = new BloomFilter();		
	
	public Book(){
		this.name = DEFAULT_NAME;
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

	public Chapter newChapter(){
		Chapter chapter = new Chapter();
		addChapter(chapter);
		return chapter;
	}
	
	public Chapter newChapter(String url){
		Chapter chapter = new Chapter();
		chapter.setUrl(url);
		addChapter(chapter);
		return chapter;
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
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return url;
	}
	
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
