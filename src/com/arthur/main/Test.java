package com.arthur.main;

import com.arthur.novel.Book;
import com.arthur.novel.Category;
import com.arthur.novel.Chapter;
import com.arthur.parser.HtmlParser;
import com.arthur.parser.SiteHtmlFilter;
import com.arthur.task.TaskThreadManager;

public class Test {

	
	public static void main(String[] argv){
		HtmlParser parser = new HtmlParser(new SiteHtmlFilter());
		TaskThreadManager manager = new TaskThreadManager();
		Category category = new Category();
		category.setUrl("http://all.qidian.com/Book/BookStore.aspx?Type=0&ChannelId=21&SubCategoryId=8&PageIndex=1&OrderId=6&P=All");
//		parser.parseCategory(category);
/*		Book book = new Book();
		book.setUrl("http://read.qidian.com/BookReader/2524770.aspx");
		parser.parseBook(book);
		Chapter chapter = new Chapter();
		chapter.setUrl("http://read.qidian.com/BookReader/2524770,44100214.aspx");
		parser.parseChapter(chapter);*/
		
		manager.addTask(category);
		manager.executeTask();
		
	}
}
