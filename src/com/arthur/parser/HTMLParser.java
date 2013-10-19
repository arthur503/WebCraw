package com.arthur.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arthur.bloomfilter.BloomFilter;
import com.arthur.crawler.Crawler;
import com.arthur.main.Main;
import com.arthur.novel.Book;
import com.arthur.novel.Category;
import com.arthur.novel.Chapter;
import com.arthur.novel.Site;
import com.arthur.task.Task;
import com.arthur.task.TaskThreadManager;
import com.arthur.utils.UniCodeConveter;

public class HtmlParser {

	static Logger logger = Logger.getLogger(Main.class.getName());
	private SiteHtmlFilter filter;
	private SimpleDateFormat df;
	private long DEFAULT_TIME;
	private BloomFilter repBloomFilter = new BloomFilter(10000);
	public static volatile int retryCounter = 0;
	public static volatile int giveUpCounter = 0;
	
	public HtmlParser(SiteHtmlFilter filter){
		this.filter = filter;
		this.df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		try {
			DEFAULT_TIME = df.parse("1970-1-1 8:00").getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if(category.getUrl() == null){
			logger.error("ERROR! CATEGORY URL IS NULL!");
			return null;
		}
		
		logger.debug("Begin to parse category urls...");
		try {
			Document doc = Jsoup.parse(new URL(category.getUrl()), 3000);

			Elements mainContent = doc.getElementsByClass("swbt");	//获取book的主页面url
			String bookUrl = "";
			for(Element e : mainContent){
//				logger.debug("book e is:"+e.toString());
				String relativeUrl = e.getElementsByAttribute("href").attr("href");
				bookUrl = Book.BASE_URL+ relativeUrl.substring(relativeUrl.lastIndexOf("/")+1);
				category.addBook(bookUrl);
//				logger.debug("category add book url:"+bookUrl);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("ERROR in Parse Category:"+category.getUrl()+"! RETRY!");
			return parseCategory(category);
		}		
		logger.debug("Parse category "+category.getName()+" URLs Done!"+
				" category url:"+category.getUrl());	
		
		return category;
	}
	
	public Book parseBook(Book book){
//		Book book = new Book("人皇");
//		String filePath = "D:\\data\\webcraw\\list.htm";
		
		if(book.getUrl() == null){
			logger.error("ERROR!BOOK URL IS NULL!");
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
				if(parseProperty(s, "BookName") != null){
					book.setName(parseProperty(s, "BookName"));
				}
				if(parseProperty(s, "AuthorName") != null){
					book.setAuthor(parseProperty(s, "AuthorName"));
				}
				if(parseProperty(s, "BookId") != null){
					book.setId(parseProperty(s, "BookId"));
				}				
			}
			
			//get update time
			Elements timeElems = doc.getElementsByAttribute("itemprop");//.getElementsContainingOwnText("dateModified");//getElementById("ADspace_title");
			Element e = timeElems.get(0);		//取第0个即为时间。——这种方法不好，需查看html源码再修改。
			book.setUpdateTime(getPassedTime(e.text()));
			
			//get chapters
			Element element = doc.getElementById("content");
			Elements links = element.getElementsByClass("list");
			
			for(Element link : links){				
				Elements scripts = link.getElementsByAttribute("href");
				for(Element script : scripts){		
					String chapterUrl = script.attr("href");
					
					//can only fetch non-vip chapter.对vipreader章节抓取不到。
					if(!chapterUrl.contains("vipreader")){			
						book.addChapter(Chapter.BASE_URL + chapterUrl);
					}
					
//					logger.debug("chapterUrl:"+chapterUrl);
				}
				
			}
						
		}catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("ERROR in Parse Book:"+book.getUrl()+"! RETRY!");
			return parseBook(book);
		}		
		logger.debug("Parse book "+book.getName()+" URLs Done!"+
				" Book url:"+book.getUrl());	
		return book;
	}

	/**
	 * Use jsoup to get txtHttpUrl from html code.
	 */
	public synchronized Chapter parseChapter(Chapter chapter){

//		String filePath = "D:\\data\\webcraw\\qidian.html";
		
		
		String chapterTxtUrl = null;
		
		try {
			Document doc = Jsoup.connect(chapter.getUrl()).get();
//			Document doc = Jsoup.parse(new File(filePath), "UTF-8");
//			Document doc = Jsoup.parse(new URL(chapter.getUrl()), 3000);
			
			//get chapter property
			Element head1 = doc.getElementById("Head1");
			Elements property = head1.getElementsByTag("script");
			for(Element element : property){
				String s = element.toString();
				if(parseProperty(s, "chapterName") != null)
				{
					chapter.setName(parseProperty(s, "chapterName"));
				}
				if(parseProperty(s, "chapterId") != null){
					chapter.setId(parseProperty(s, "chapterId"));
				}
				
			}
			
			//get chapter update time
			Element timeElement = doc.getElementById("lblLastUpdateTime");
			chapter.setUpdateTime(getPassedTime(timeElement.text()));
//			logger.debug(chapter.getLastUpdateTime());
			
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
			
			//将出错的chapter使用repBloomFilter检查。若为新url，则重试一次，否则放弃。
			if(repBloomFilter.checkAndAdd(chapter.getUrl())){
				HtmlParser.retryCounter ++;
				Task task = new Task(chapter);
				logger.error("\n>>>>ERROR in parse Chapter "+chapter.getName()+":"+chapter.getUrl()
						+ " Add to tasklist again. Task Id:"+task.getId()+
						" retryCounter:"+HtmlParser.retryCounter+" giveUpCounter:"+HtmlParser.giveUpCounter+"\n");
				TaskThreadManager.addTask(task);
			}else{
				HtmlParser.giveUpCounter ++;
				logger.error("\n>>>>ERROR in parse Chapter "+chapter.getName()+":"+chapter.getUrl()
						+ ". Have Retried once, so give up."+
						" retryCounter:"+HtmlParser.retryCounter+" giveUpCounter:"+HtmlParser.giveUpCounter+"\n");
			}
//			e.printStackTrace();
		}
//		logger.debug("Parse chapter "+chapter.getName()+" Done!");	
		return chapter;
	}


	//【待优化】http://www.oschina.net/question/587638_60715 上说解析script可以用ScriptEngine(執行js)提取信息。
	private String parseProperty(String s, String tag) {
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
	
	
	private long getPassedTime(String time) {
		// TODO Auto-generated method stub
		long t = 0;
		try {
			t = df.parse(time).getTime() - DEFAULT_TIME;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return t;
	}


}
