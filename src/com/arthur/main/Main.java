package com.arthur.main;

import org.apache.log4j.Logger;

import com.arthur.bookwriter.BookWriter;
import com.arthur.novel.Book;
import com.arthur.novel.Category;
import com.arthur.novel.Chapter;
import com.arthur.novel.Site;
import com.arthur.parser.HtmlParser;
import com.arthur.parser.SiteHtmlFilter;
import com.arthur.task.Task;
import com.arthur.task.TaskThreadManager;

public class Main {

	public static Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] argv){
		
		String bookUrl = "http://read.qidian.com/BookReader/2524770.aspx";
		String chapterUrl = "http://read.qidian.com/BookReader/2524770,44100214.aspx";
		String categoryUrl = "http://all.qidian.com/Book/BookStore.aspx?Type=0&ChannelId=21&SubCategoryId=8&PageIndex=1&OrderId=6&P=All";
		HtmlParser parser = new HtmlParser(new SiteHtmlFilter());
		TaskThreadManager tm = new TaskThreadManager();
		
		System.out.println("Begin webcraw...");
		
		Site site = new Site("qidian");
		Category category = site.newCategory();		
		category.setUrl(categoryUrl);
		category.addBook(bookUrl);
//		book.setUrl(bookUrl);
/*		book.setUrl(bookUrl);
		parser.parseBook(book);*/
		
		tm.addTask(category);
//		tm.addTask(book);	
		logger.debug("Task count:"+tm.size());
//		tm.addTask(book);	
		
		//write book
/*		BookWriter bw = new BookWriter();
		for(int i=0;i<book.getChapters().size() && i<30;i++){
			Chapter chapter = book.getChapter(i);
//			parser.parseChapter(chapter);
			tm.addTask(new Task(chapter));
//			bw.writeChapter(chapter);
		}	*/	
//		logger.debug("Add Book "+book.getName()+" to TaskThreadManager Done!");
		
//		book = parser.parseBook(book);
		
		//test taskmanager

/*		//测试多线程，添加100个相同任务。
		Task task;
		for(int i=0;i<100;i++){
			Chapter chapter = new Chapter();
			chapter.setUrl(chapterUrl);
			chapter.setName("Chapter "+i);
			tm.addTask(new Task(chapter));
		}*/
//		logger.debug("tasklist info:\n"+tm.getTaskListInfo());
		logger.debug("Task count:"+tm.size());

//		tm.executeTask();
		
		logger.debug("task manager in main done!");

		

	}
	


}
