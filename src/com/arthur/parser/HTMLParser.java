package com.arthur.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arthur.crawler.Crawler;
import com.arthur.main.Main;
import com.arthur.novel.Book;
import com.arthur.novel.Category;
import com.arthur.novel.Chapter;
import com.arthur.novel.Site;
import com.arthur.utils.UniCodeConveter;

public class HtmlParser {

	static Logger logger = Logger.getLogger(Main.class.getName());
	private SiteHtmlFilter filter;
	
	public HtmlParser(SiteHtmlFilter filter){
		this.filter = filter;
	}
	
	/**
	 * 待补充
	 * @param site
	 * @return
	 */
	public Site parseSite(Site site){
		//TODO...
		
		return site;
	}
	
	
	/**
	 * 待补充
	 * @param category
	 * @return
	 */
	public Category parseCategory(Category category){
		//TODO...
		
		return category;
	}
	
	public Book parseBook(Book book){
//		Book book = new Book("人皇");
//		String filePath = "D:\\data\\webcraw\\list.htm";
		
		if(book.getUrl() == null){
			logger.error("NULL BOOK URL!");
			return null;
		}
		
//		List<String> chapterUrls = new LinkedList<String>(); 
		
		try {
			Document doc = Jsoup.parse(new URL(book.getUrl()), 3000);
//			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
			
			//get property
			Element head1 = doc.getElementById("Head1");
			Elements property = head1.getElementsByTag("script");
			for(Element element : property){
				String s = element.toString();
				if(getChapterProperty(s, "BookName") != null){
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
			
			for(Element link : links){				
				Elements scripts = link.getElementsByAttribute("href");
				for(Element script : scripts){		
					String chapterUrl = script.attr("href");
					//can only fetch non-vip chapter.
					if(!chapterUrl.contains("vipreader")){
						chapterUrl = Chapter.BASE_URL + chapterUrl;	
//						chapterUrls.add(chapterUrl);
						book.newChapter(chapterUrl);
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
		logger.debug("Parse book "+book.getName()+" Done!");	
		return book;
	}
	
	/**
	 * Use jsoup to get txtHttpUrl from html code.
	 */
	public Chapter parseChapter(Chapter chapter){

//		String filePath = "D:\\data\\webcraw\\qidian.html";
		
		String chapterTxtUrl = null;
		
		try {
//			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
//			Document doc = Jsoup.parse(new URL(chapter.getUrl()), 3000);
			Document doc = Jsoup.connect(chapter.getUrl()).get();
			
			//get chapter property
			Element head1 = doc.getElementById("Head1");
			Elements property = head1.getElementsByTag("script");
			for(Element element : property){
				String s = element.toString();
				if(getChapterProperty(s, "chapterName") != null)
				{
					chapter.setName(getChapterProperty(s, "chapterName"));
				}
				if(getChapterProperty(s, "chapterId") != null){
					chapter.setId(getChapterProperty(s, "chapterId"));
				}
				
			}
			
			//get chapter content
			Elements links = doc.getElementsByClass("bookcontent");
			for(Element link : links){
				Elements scripts = link.getElementsByAttribute("src");
				for(Element script : scripts){		
					chapterTxtUrl = script.attr("src");
					Crawler crawler = new Crawler();
					String content = crawler.getTextFile(chapterTxtUrl);
					
					//【待优化】注：此处可以使用正则表达式来去除<p></p>标签！
					content = content.replaceAll("<p>", "\n");
					content = content.replace("</p>", "\n");
					chapter.setContent(content);
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("Parse chapter "+chapter.getName()+" Done!");	
		return chapter;
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
