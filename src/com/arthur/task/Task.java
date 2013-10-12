package com.arthur.task;

import com.arthur.novel.Chapter;

public class Task {
	
	private int id;
	private Chapter chapter;
	
	public Task(Chapter chapter){
		this.chapter = chapter;
	}
	
	public Task(int id){
		this.id = id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}

	public Chapter getChapter(){
		return chapter;
	}
}
