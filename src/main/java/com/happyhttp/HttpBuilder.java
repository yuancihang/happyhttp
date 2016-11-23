package com.happyhttp;

import java.io.File;

/**
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class HttpBuilder {

	private HttpRequest request;
	private Form form = new Form();

	public HttpRequest getRequest() {
		return request;
	}

	public HttpBuilder setRequest(HttpRequest request) {
		this.request = request;
		return this;
	}
	
	public HttpBuilder setProxy(String proxyHost, int proxyPort, String username, String password){
		this.request.setProxy(proxyHost, proxyPort, username, password);
		return this;
	}
	
	/**
	 * 添加http请求头
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpBuilder addHeader(String name, String value){
		request.addHeader(name, value);
		return this;
	}
	
	public HttpBuilder addFormField(String name, String value){
		form.addFormField(name, value);
		return this;
	}
	
	public HttpBuilder setForm(Form form) {
		if(request instanceof HttpGet){
			((HttpGet)request).setForm(form);
		}else if(request instanceof HttpPost){
			((HttpPost)request).setFormBody(form);
		}
		return this;
	}
	
	public HttpBuilder setJsonBody(String json){
		if(request instanceof HttpPost){
			((HttpPost)request).setJsonBody(json);
		}
		return this;
	}
	
	public HttpBuilder apply(){
		if(!form.isEmpty()){
			setForm(form);
		}
		return this;
	}

	public HttpResponse execute(){
		apply();
		
		return HttpClient.defaultClient().execute(request);
	}
	
	public void download(File file){
		apply();
		
		HttpClient.defaultClient().download(request, file);
	}
	
	public static HttpBuilder newPost(String uri){
		return new HttpBuilder().setRequest(new HttpPost(uri));
	}
	
	public static HttpBuilder newGet(String uri){
		return new HttpBuilder().setRequest(new HttpGet(uri));
	}
}