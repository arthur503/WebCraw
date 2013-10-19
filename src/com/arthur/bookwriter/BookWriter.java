package com.arthur.bookwriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.arthur.main.Main;
import com.arthur.novel.Book;
import com.arthur.novel.Chapter;

public class BookWriter {
	
	static Logger logger = Logger.getLogger(Main.class.getName());
	private String baseUrl = "D:\\data\\webcraw";
	
	public BookWriter(){
		
	}
	
	/**
	 * 写入Book文件夹
	 * @param book
	 */
/*	public void write(Book book){
		

		for(int i=0;i<book.getChapters().size();i++){
			Chapter chapter = book.getChapter(i);
			
			StringBuffer sb = new StringBuffer()
			.append(chapter.getBook().getCategory().getSite().getSiteBaseUri()).append("\\")			
			.append(chapter.getBook().getCategory().getSite().getName()).append("\\")			
			.append(chapter.getBook().getCategory().getName()).append("\\")			
			.append(chapter.getBook().getName());			
			
			String dirPath = sb.toString();
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			File chapterFile = new File(dirPath+ "\\"+chapter.getName()+".txt");			
			try {
				Writer bw = new OutputStreamWriter(new FileOutputStream(chapterFile));			
				bw = new OutputStreamWriter(new FileOutputStream(chapterFile));		
				bw.write(chapter.toString());							
				bw.close();
				logger.debug("Write File "+chapterFile.getAbsolutePath()+" Done!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.debug("Write Book "+book.getName()+" Done!");
	}
*/
	public synchronized void writeChapter(Chapter chapter){

//		Chapter chapter = book.getChapter(i);
		
		//检查内容为空则为出错
		if(chapter.getContent() == null){
			logger.error("ERROR in BookWriter: CHAPTER IS NULL!");
			return;
		}
		
		StringBuffer sb = new StringBuffer()
		.append(chapter.getBook().getCategory().getSite().getSiteBaseUri()).append("\\")			
		.append(chapter.getBook().getCategory().getSite().getName()).append("\\")			
		.append(chapter.getBook().getCategory().getName()).append("\\")			
		.append(chapter.getBook().getName());			
		
		String dirPath = sb.toString();
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		//注：此处需要检查下chapter的name中是否包含非法字符
		String checkedFileName = checkFileName(chapter.getName());
		File chapterFile = new File(dirPath+ "\\"+checkedFileName+".txt");	
		
		try {
			Writer bw = new OutputStreamWriter(new FileOutputStream(chapterFile));			
			bw = new OutputStreamWriter(new FileOutputStream(chapterFile));		
			bw.write(chapter.toString());							
			bw.close();
			logger.debug("Write File "+chapterFile.getAbsolutePath()+" Done!");
//			logger.debug("Write Chapter "+chapter.getName()+" Done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("ERROR in Write Chapter "+chapter.getName()+"!");
			e.printStackTrace();
		}
	
	}

	/**
	 * 确保filename中不包含非法字符。
	 *！！！ 需完善（使用正则表达式？）。
	 * @param name
	 * @return
	 */
	private String checkFileName(String name) {
		// TODO Auto-generated method stub
		if(name.contains("?")){
			name.replace("?", "_");
		}
		return name;
	}
	
}
