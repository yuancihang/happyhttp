package com.happyhttp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.happyhttp.util.GzipUtil;
import com.happyhttp.util.StreamUtil;

public class HttpClient {
	
	private static HttpClient instance = new HttpClient();
	
	public static HttpClient defaultClient(){
		return instance;
	}
	
	public void download(HttpRequest request, File file) {
		HttpURLConnection conn = null;
		
		try{
			conn = doRequest(request);
			
			// 保存响应结果到文件
			readResponseAsFile(conn, file);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			if(conn != null)
				conn.disconnect();
		}
	}

	public HttpResponse execute(HttpRequest request){
		HttpResponse response = new HttpResponse();
		HttpURLConnection conn = null;
		try {
			conn = doRequest(request);
			
			// http响应码
			response.setCode(conn.getResponseCode());
			// http响应头
			for(String field : conn.getHeaderFields().keySet()){
				response.addHeader(field, conn.getHeaderField(field));
			}
			
			// 读取响应
			byte[] r = readResponse(conn);
			// 支持GZIP返回
			String encoding = conn.getHeaderField("Content-Encoding");
			if((encoding != null) && (encoding.equals("gzip"))){
				r = GzipUtil.uncompress(r);
			}
			
			response.setBody(r);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally{
			if(conn != null)
				conn.disconnect();
		}
		
		return response;
	}
	
	private HttpURLConnection doRequest(HttpRequest request) throws IOException{
		URL url = new URL(request.getUri());
		HttpURLConnection conn = null;
		if(request.hasProxy()){
			conn = (HttpURLConnection)url.openConnection(request.getProxy());
		}else{
			conn = (HttpURLConnection)url.openConnection();
		}
		
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(request.getConnectTimeout());
		conn.setReadTimeout(request.getReadTimeout());
		conn.setRequestMethod(request.getMethod());
		
		for(String headerName : request.getHeaderMap().keySet()){
			conn.setRequestProperty(headerName, request.getHeaderMap().get(headerName));
		}
		
		conn.connect();
		
		// add body
		if(request.getBodyData() != null){
			conn.getOutputStream().write(request.getBodyData());
			conn.getOutputStream().flush();
		}
		
		return conn;
	}
	
	private void readResponseAsFile(HttpURLConnection conn, File file)
			throws IOException {
		InputStream is = conn.getErrorStream();
		if (is == null) {
			is = conn.getInputStream();
		}
		
		StreamUtil.readStreamAsFile(is, file, false);
	}
	
	private byte[] readResponse(HttpURLConnection conn) throws IOException{
		InputStream is = conn.getErrorStream();
		if(is == null){
			is = conn.getInputStream();
		}
		
		return readInputStream(is);
	}
	private byte[] readInputStream(InputStream is)throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int data = 0;
		while((data = is.read()) != -1){
			baos.write(data);
		}
		
		return baos.toByteArray();
	}
}
