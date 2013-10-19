package com.arthur.task;

import org.apache.log4j.Logger;

import com.arthur.bookwriter.BookWriter;
import com.arthur.main.Main;
import com.arthur.novel.Chapter;
import com.arthur.parser.HtmlParser;
import com.arthur.parser.SiteHtmlFilter;

public class TaskThread extends Thread{


	public static Logger logger = Logger.getLogger(Main.class.getName());
	private int id;
	private Task task;
	private Chapter chapter;
	private TaskThreadManager taskManager;
	private BookWriter bw = new BookWriter();
	private HtmlParser parser = new HtmlParser(new SiteHtmlFilter());
	
	public TaskThread(TaskThreadManager taskManager, int id){
		this.id = id;
		this.taskManager = taskManager;
//		logger.debug("Create task thread id:"+id);
	}

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
//		logger.debug("In Thread id "+id+" run().");
		
			Task task;
			while((task = taskManager.getTask()) != null){
//				logger.debug("in while loop.");
//					synchronized(task){	
//					logger.debug("in synchronized begin.");
					chapter = task.getChapter();
					parser.parseChapter(chapter);
					
					chapter.setName("Task_id_"+String.format("%1$06d", task.getId())+"_"+chapter.getName());						
					logger.debug("TaskThread "+id+" parse TASK "+task.getId()+" chapter "+chapter.getName()+" done!");
					
					bw.writeChapter(task.getChapter());	
//					logger.debug("in synchronized end.");			
//				}	
			}
		
	}
	
	
}
