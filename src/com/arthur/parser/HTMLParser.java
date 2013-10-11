package com.arthur.parser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arthur.bookfile.BookWriter;
import com.arthur.crawler.Crawler;
import com.arthur.crawler.Main;
import com.arthur.novel.Book;
import com.arthur.novel.Chapter;
import com.arthur.utils.UniCodeConveter;

public class HTMLParser {

	static Logger logger = Logger.getLogger(Main.class.getName());
	private HTMLFilter filter;
	
	public HTMLParser(HTMLFilter filter){
		this.filter = filter;
	}
	
	public Book getBook(String novelUrl){
		Book book = new Book("人皇");
//		String filePath = "D:\\data\\webcraw\\list.htm";
		
		List<String> chapterUrls = new LinkedList<String>(); 
		
		try {
			Document doc = Jsoup.parse(new URL(novelUrl), 3000);
//			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
			
			//get property
			Element head1 = doc.getElementById("Head1");
			Elements property = head1.getElementsByTag("script");
			for(Element element : property){
				String s = element.toString();
				if(getChapterProperty(s, "BookName") != null){
					logger.debug(new String(getChapterProperty(s, "BookName")));
					book.setName(getChapterProperty(s, "BookName"));
				}
				if(getChapterProperty(s, "AuthorName") != null){
					book.setAuthor(getChapterProperty(s, "AuthorName"));
				}
				if(getChapterProperty(s, "BookId") != null){
					book.setId(getChapterProperty(s, "BookId"));
				}				
			}
			
			//get chapters
			Element element = doc.getElementById("content");
			Elements links = element.getElementsByClass("list");
			String chapterBaseUrl = "http://read.qidian.com";
			for(Element link : links){				
				Elements scripts = link.getElementsByAttribute("href");
				for(Element script : scripts){		
					String chapterUrl = script.attr("href");
					//can only fetch non-vip chapter.
					if(!chapterUrl.contains("vipreader")){
						chapterUrl = chapterBaseUrl+chapterUrl;	
						chapterUrls.add(chapterUrl);
					}
//					logger.debug("chapterUrl:"+chapterUrl);
				}
				
			}
						
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//仅测试前30章
		BookWriter bw = new BookWriter();
		for(int i=0;i<chapterUrls.size();i++){
			Chapter chapter = getChapter(chapterUrls.get(i));
			chapter.setBook(book);
			book.addChapter(chapter);
//			bw.writeChapter(chapter);
		}
		
//		logger.debug(book.toString());
		return book;
	}
	
	/**
	 * Use jsoup to get txtHttpUrl from html code.
	 */
	public Chapter getChapter(String chapterUrl){

//		String filePath = "D:\\data\\webcraw\\qidian.html";
		
		Chapter c = new Chapter();		
		String txtHttpUrl = null;
		
		try {
//			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
			Document doc = Jsoup.connect(chapterUrl).get();
			
			//get chapter property
			Element head1 = doc.getElementById("Head1");
			Elements property = head1.getElementsByTag("script");
			for(Element element : property){
				String s = element.toString();
				if(getChapterProperty(s, "chapterName") != null)
				{
					c.setName(getChapterProperty(s, "chapterName"));
				}
				if(getChapterProperty(s, "chapterId") != null){
					c.setId(getChapterProperty(s, "chapterId"));
				}
				
			}
			
			//get chapter content
			Elements links = doc.getElementsByClass("bookcontent");
			for(Element link : links){
				Elements scripts = link.getElementsByAttribute("src");
				for(Element script : scripts){		
					txtHttpUrl = script.attr("src");
					Crawler crawler = new Crawler();
					String content = crawler.getTextFile(txtHttpUrl);
					
					//【待优化】注：此处可以使用正则表达式来去除<p></p>标签！
					content = content.replaceAll("<p>", "\n");
					content = content.replace("</p>", "\n");
					c.setContent(content);
//					logger.debug("Txt Url:"+txtHttpUrl);
					logger.debug("Get chapter "+c.getName()+" CONTENT Done!");					
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	//【待优化】http://www.oschina.net/question/587638_60715 上说解析script可以用ScriptEngine(執行js)提取信息。
	private String getChapterProperty(String s, String tag) {
		// TODO Auto-generated method stub
		if(s.indexOf(tag) > -1){
			String subString = s.substring(s.indexOf(tag));
			String tagContent = subString.substring(subString.indexOf("'")+1,subString.indexOf(",")-1);
			tagContent = UniCodeConveter.unicode2Str(tagContent);
//			logger.debug("Get TAG ["+tag+"] content:"+tagContent);
			return tagContent;
		}
		return null;
	}
	

}
