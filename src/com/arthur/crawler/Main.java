package com.arthur.crawler;

import org.apache.log4j.Logger;

import com.arthur.bookfile.BookWriter;
import com.arthur.novel.Book;
import com.arthur.novel.Category;
import com.arthur.novel.Site;
import com.arthur.parser.HTMLFilter;
import com.arthur.parser.HTMLParser;

public class Main {

	static Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] argv){
		
		String bookUrl = "http://read.qidian.com/BookReader/2524770.aspx";
		HTMLParser parser = new HTMLParser(new HTMLFilter());
		
		Site site = new Site("qidian");
		Category category = new Category("Test Category");		
		Book book = new Book();		
		
		book = parser.getBook(bookUrl);
		category.addBook(book); 		
		site.addCategory(category);
		
/*		BookWriter bw = new BookWriter();
		bw.write(book);*/
		

	}
	


}
