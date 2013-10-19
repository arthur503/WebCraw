package com.arthur.novel;

public class Chapter {
	
	private Book book = new Book();
	private String name;
	private String DEFAULT_NAME = "CHAPTER";
	private String url;
	private String id;
	private String content;
	public static final String BASE_URL = "http://read.qidian.com";
	private long updateTime;
	
	public Chapter(){
		this.name = DEFAULT_NAME;
	}

	public Chapter(String name){
		this.name = name;
	}
	
	public void setBook(Book book){
		this.book = book;
		//若要写入文件需添加后缀。
//		this.uri = book.getUri() + "\\" + this.getName();	
	}
	
	public Book getBook(){
		return book;
	}	
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public long getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(long i){
		this.updateTime = i;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Book Name:"+this.getBook().getName()+"\nChapter Name:"+
				this.getName()+"\nChapter Id:"+this.getId()+"\nChapter Content:\n"+this.getContent();
	}
}
