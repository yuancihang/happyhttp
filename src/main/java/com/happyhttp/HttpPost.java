package com.happyhttp;

import java.io.UnsupportedEncodingException;

public class HttpPost extends HttpRequest {

	private String charset;
	
	public HttpPost(String uri) {
		this(uri, "UTF-8");
		
		this.method = "POST";
	}
	
	public HttpPost(String uri, String charset) {
		super(uri);
		this.charset = charset;
	}
	
	public void setFormBody(Form form){
		if((form == null) || (form.isEmpty())){
			return ;
		}
		
		addHeader("Content-Type", "application/x-www-form-urlencoded;charset="+charset);
		
		try {
			this.bodyData = form.buildParamter().getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public void setMultiPartFormBody(MultiPartForm form){
		if((form == null) || (form.isEmpty())){
			return ;
		}
		
		addHeader("Content-Type", form.getContentType());
		
		this.bodyData = form.getData();
	}
	
	public void setJsonBody(String json){
		if(json == null){
			return ;
		}
		
		addHeader("Content-Type", "application/json;charset="+charset);
		
		try {
			this.bodyData = json.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
