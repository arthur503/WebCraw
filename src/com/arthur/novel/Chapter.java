package com.arthur.novel;

public class Chapter {
	
	private Book book;
	private String name;
	private String uri;
	private String id;
	private String content;
	
	public Chapter(){

	}

	public void setBook(Book book){
		this.book = book;
		//若要写入文件需添加后缀。
//		this.uri = book.getUri() + "\\" + this.getName();	
	}
	
	public Book getBook(){
		return book;
	}	
	
/*	public void setUri(String uri){
		this.uri = uri;
	}
	
	public String getUri(){
		return uri;
	}*/
	
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
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Book Name:"+this.getBook().getName()+"\nChapter Name:"+
				this.getName()+"\nId:"+this.getId()+"\nContent:\n"+this.getContent();
	}
}
