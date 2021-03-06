package com.arthur.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arthur.main.Main;

public class Crawler{

	static Logger logger = Logger.getLogger(Main.class.getName());
	
	public Crawler(){
//		log.debug("Create a new crawler.");
	}
	
	/**
	 * @deprecated 
	 * Use HTMLParser instead.
	 * @param url
	 * @return
	 */
	public String getHtmlCode(String url){
		String htmlCode = "";
		try{
			
			URL httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
//			connection.connect();
			
			InputStream is = connection.getInputStream();
			
			byte[] buffer = new byte[512];
			int length = -1;
			String tmp = "";
			while((length = is.read(buffer, 0, 512)) != -1){
				tmp = new String(buffer, 0, 512);
				htmlCode += tmp;
//				System.out.println(tmp);
			}
			is.close();
			connection.disconnect();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(htmlCode == ""){
			System.out.println("ERROR!GET NULL HTML!");
		}
		return htmlCode;
		
	}
	

	//不catch exception，交由调用类处理。
	public synchronized String getTextFile(String url) throws IOException {
//		logger.debug("Craw to get Txt content from:"+url);
		
		String textCode = "";
//		try{
			
			URL httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
//			connection.connect();
			connection.setRequestProperty("Accept-Charset","GB2312");
			connection.setRequestProperty("Connection", "keep-alive");
			
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"GB2312"));
			
			String tmp = "";
			while((tmp = br.readLine()) != null){
				textCode += tmp;
			}
			is.close();
			connection.disconnect();
			
//		}
/*		catch(Exception e){
			e.printStackTrace();
		}*/
		if(textCode == ""){
			System.out.println("ERROR!GET NULL HTML IN CRAWLER!");
		}
		
		return textCode;
		
	}
	
}


