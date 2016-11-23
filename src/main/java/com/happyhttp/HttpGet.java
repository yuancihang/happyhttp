package com.happyhttp;

public class HttpGet extends HttpRequest {

	private Form form;
	
	public HttpGet(String uri) {
		super(uri);
		
		this.method = "GET";
	}
	
	public void setForm(Form form){
		this.form = form;
	}

	@Override
	public String getUri() {
		if(form != null){
			String p = form.buildParamter();
			
			if(p.length() > 0){
				StringBuilder urlBuilder = new StringBuilder();
				urlBuilder.append(uri);
				if(uri.indexOf('&') != -1){//已经有参数
					urlBuilder.append("&" + p);
				}else{
					urlBuilder.append("?" + p);
				}
				return urlBuilder.toString();
			}
		}
		
		return uri;
	}
}
