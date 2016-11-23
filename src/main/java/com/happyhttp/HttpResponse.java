package com.happyhttp;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
	/** 响应码 */
	private int code;
	/** 响应头 */
	private Map<String, String> headerMap = new HashMap<String, String>();
	/** 响应体 */
	private byte[] body;
	
	public boolean isHttpOk(){
		return code == HttpURLConnection.HTTP_OK;
	}
	
	public void addHeader(String name, String value){
		headerMap.put(name, value);
	}
	
	public String getBodyAsText(){
		return getBodyAsText("UTF-8");
	}
	
	public String getBodyAsText(String charset) {
		try {
			return new String(body, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public byte[] getBodyAsBytes(){
		return body;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
}
