package com.arthur.main;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.arthur.novel.Chapter;
import com.arthur.parser.HtmlParser;
import com.arthur.parser.SiteHtmlFilter;
import com.arthur.task.Task;
import com.arthur.task.TaskManager;

public class Main {

	public static Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] argv){
		
		String bookUrl = "http://read.qidian.com/BookReader/2524770.aspx";
		HtmlParser parser = new HtmlParser(new SiteHtmlFilter());
		
/*		Site site = new Site("qidian");
		Category category = site.newCategory();		
		Book book = category.newBook();
		book.setUrl(bookUrl);
		logger.debug(book.getUrl());		*/
		
		//仅测试前30章
/*		BookWriter bw = new BookWriter();
		for(int i=0;i<book.getChapters().size();i++){
			Chapter chapter = book.getChapter(i);
			parser.parseChapter(chapter);
			bw.writeChapter(chapter);
		}		
		logger.debug("Write Book "+book.getName()+" Done!");*/
		
//		book = parser.parseBook(book);
		
		//test taskmanager
		String chapterUrl = "http://read.qidian.com/BookReader/2524770,44100214.aspx";

		TaskManager tm = new TaskManager();
		//测试多线程，添加100个相同任务。
		Task task;
		for(int i=0;i<100;i++){
			Chapter chapter = new Chapter();
			chapter.setUrl(chapterUrl);
			chapter.setName("Chapter "+i);
			tm.addTask(new Task(chapter));
		}
//		logger.debug("tasklist info:\n"+tm.getTaskListInfo());
		logger.debug("task count:"+tm.size());

		tm.executeTask();
		
		logger.debug("task manager in main done!");

		

	}
	


}
