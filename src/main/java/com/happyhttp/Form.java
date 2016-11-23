package com.happyhttp;

import java.util.HashMap;
import java.util.Map;

public class Form {
	
	/** 表单参数 */
	private Map<String, String> form = new HashMap<String, String>();
	
	public boolean isEmpty(){
		return form.isEmpty();
	}

	/**
	 * 添加表单字段
	 * @param name
	 * @param value
	 * @return
	 */
	public Form addFormField(String name, String value){
		form.put(name, value);
		return this;
	}
	
	public String buildParamter(){
		StringBuilder sb = new StringBuilder();
		if(notEmpty(form)){
			for(Map.Entry<String, String> entry : form.entrySet()){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		sb = compareAndDeleteLastChar(sb, '&');
		return sb.toString();
	}
	
	private boolean notEmpty(Map<?,?> map) {
		return (map != null) && (!map.isEmpty());
	}
	
	private StringBuilder compareAndDeleteLastChar(StringBuilder sb, char c){
		if((sb.length() > 0) && (sb.charAt(sb.length() - 1) == c)){
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}
	
}
