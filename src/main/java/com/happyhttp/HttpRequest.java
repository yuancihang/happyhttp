package com.happyhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpRequest {

	/** http请求方法 */
	protected String method;
	protected String uri;
	/** http请求头 */
	protected Map<String, String> headerMap = new HashMap<String, String>();
	/** body */
	protected byte[] bodyData;
	/** 连接超时 */
	protected int connectTimeout = 30000;
	/** 读超时 */
	protected int readTimeout = 30000;
	/** 代理 */
	protected Proxy proxy;
	
	public HttpRequest(String uri){
		this.uri = uri;
		
		addHeader("Connection", "close");
		addHeader("Accept-Encoding", "gzip");
	}
	
	/**
	 * 添加http请求头
	 * @param name
	 * @param value
	 * @return
	 */
	public void addHeader(String name, String value){
		headerMap.put(name, value);
	}
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	public void setProxy(String proxyHost, int proxyPort, String username, String password){
		proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
		
		if(username != null){
			byte[] auth = Base64.getEncoder().encode(new String(username+":"+password).getBytes());
			headerMap.put("Proxy-Authorization", "Basic " + new String(auth));
		}
	}
	
	public boolean hasProxy(){
		return proxy != null;
	}

	public String getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public byte[] getBodyData() {
		return bodyData;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public Proxy getProxy() {
		return proxy;
	}
	
}
