package com.arthur.task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.arthur.main.Main;
import com.arthur.novel.Book;
import com.arthur.novel.Category;
import com.arthur.novel.Chapter;
import com.arthur.parser.HtmlParser;
import com.arthur.parser.SiteHtmlFilter;

public class TaskThreadManager {

	public static Logger logger = Logger.getLogger(Main.class.getName());
	private int totalTaskCount = 0;
	private int TASK_THREAD_COUNT = 90;
	private int taskThreadId = 0;
	private volatile static List<Task> taskList = new LinkedList<Task>();
	private HtmlParser parser;
	
	/**
	 * TODO：
	 * 1.保存任务列表；
	 * 2.启动多线程处理任务。
	 */
	public TaskThreadManager(){
		parser = new HtmlParser(new SiteHtmlFilter());
	}
	
	public static synchronized void addTask(Task task){
		taskList.add(task);
	}
	
	public void addTask(Book book){
		if(book.getUrl() == null){
			logger.error("BOOK URL IS NULL!");
			return;
		}
		
		//每次添加都需要重新解析book页面，查看有无新章节
		parser.parseBook(book);

		for(int i=book.getLastUpdateChapterIndex();i<book.getChapters().size();i++){
			addTask(new Task(book.getChapter(i)));
		}
		book.setLastUpdateChapterIndex(book.getChapters().size());
		
		logger.debug("Add book "+book.getName()+" URLs to TaskList done!"+"Task count:"+this.taskList.size());
	}
	
	
	public void addTask(Category category){
		if(category.getUrl() == null){
			return;
		}
		parser.parseCategory(category);
		
		for(int i=0;i<category.getBookList().size();i++){
			addTask(category.getBook(i));
		}
	}
	
	public synchronized Task getTask(){
			if(taskList.size() > 0){
				return taskList.remove(0);
			}
			return null;
	}
	
	public boolean hasTask(){
		if(taskList.size() > 0){
			return true;
		}
		return false;
	}
	
	
	public Task getTask(int i){
		return taskList.get(i);
	}
	
	public void executeTask(){
		ExecutorService exec = Executors.newCachedThreadPool();
//		ExecutorService exec = Executors.newFixedThreadPool(TASK_THREAD_COUNT);
		for(int i=0;i<TASK_THREAD_COUNT;i++){
			exec.execute(new TaskThread(this, taskThreadId++));
		}
		exec.shutdown();
	}
	
	public int size(){
		return taskList.size();
	}
	
	public int getTotalTaskCount(){
		return totalTaskCount;
	}

	public String getTaskListInfo(){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<taskList.size();i++){
			Task task = taskList.get(i);
			sb.append("Task "+i+" id:"+task.getId()+" Chapter Name:"+
					task.getChapter().getName()+" Chapter Url:"+task.getChapter().getUrl()+"\n");
		}
		return sb.toString();
	}

}
