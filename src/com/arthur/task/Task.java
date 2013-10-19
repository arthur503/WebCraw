package com.arthur.task;

import com.arthur.novel.Chapter;

public class Task {
	
	private int id;
	private Chapter chapter;
	private static int taskId = 0;
	
	public Task(Chapter chapter){
		this.chapter = chapter;
		this.id = taskId++;
	}
	
/*	public Task(int id){
		this.id = id;
	}*/
	
/*	public void setId(int id){
		this.id = id;
	}*/
	
	public int getId(){
		return id;
	}

	public Chapter getChapter(){
		return chapter;
	}
}
