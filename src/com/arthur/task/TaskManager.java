package com.arthur.task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.arthur.main.Main;
import com.arthur.novel.Chapter;
import com.arthur.parser.HtmlParser;
import com.arthur.parser.SiteHtmlFilter;

public class TaskManager {

	public static Logger logger = Logger.getLogger(Main.class.getName());
	private List<Task> taskList = new LinkedList<Task>();
	private int totalTaskCount = 0;
	private int taskId = 0;
	private int TASK_THREAD_COUNT = 10;
	private int taskThreadId = 0;
	
	/**
	 * TODO：
	 * 1.保存任务列表；
	 * 2.启动多线程处理任务。
	 */
	public TaskManager(){
		
	}
	
	public void addTask(Task task){
		task.setId(taskId++);
		taskList.add(task);
	}
	
	public Task getTask(){
		if(taskList.size() > 0){
			return taskList.remove(0);
		}
		return null;
	}
	
	public Task getTask(int i){
		return taskList.get(i);
	}
	
	public void executeTask(){
		ExecutorService exec = Executors.newFixedThreadPool(TASK_THREAD_COUNT);
		for(int i=0;i<TASK_THREAD_COUNT;i++){
			exec.execute(new TaskThread(this, taskThreadId++));
		}
		exec.shutdown();
		
		logger.debug("executeTask done!");
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
