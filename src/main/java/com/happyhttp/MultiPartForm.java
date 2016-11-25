package com.happyhttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MultiPartForm {
	private static final String MULTIPART_CHARS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private String boundarystr;
	private String boundary;
	private String endBoundary;
	private Map<String, Object> form = new HashMap<String, Object>();
	
	public MultiPartForm(){
		boundarystr = generateMultipartBoundary();
		boundary = "------" + boundarystr + "\r\n";
		endBoundary = boundary + "--";
	}
	
	private String generateMultipartBoundary() {
		Random rand = new Random();
		char[] bytes = new char[rand.nextInt(11) + 30]; // a random size
														// from 30 to 40
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = MULTIPART_CHARS.charAt(rand.nextInt(MULTIPART_CHARS.length()));
		}
		
		return new String(bytes);
	}
	
	public boolean isEmpty(){
		return form.isEmpty();
	}
	
	public void addMultiPartFormField(String name, Object value){
		form.put(name, value);
	}
	
	public String getContentType(){
		return "multipart/form-data;boundary=" + boundarystr;
	}
	
	public byte[] getData(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		writeToStream(baos);
		
		return baos.toByteArray();
	}
	
	public void writeToStream(OutputStream os){
		if(!form.isEmpty()){
			try {
				for(Map.Entry<String, Object> entry : form.entrySet()){
						if(entry.getValue() instanceof byte[]){
							writeBytesField(entry.getKey(), (byte[])entry.getValue(), os);
						}else{
							writeStringField(entry.getKey(), String.valueOf(entry.getValue()), os);
						}
					
				}
				
				os.write(endBoundary.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
	
	private void writeStringField(String name, String value, OutputStream os)throws IOException{
		StringBuilder sb = new StringBuilder();
		
		sb.append(boundary);
		sb.append("Content-Disposition:form-data;name=\""+name+"\"");
		sb.append("\r\n\r\n\r\n");
		sb.append(value);
		sb.append("\r\n\r\n");
		
		os.write(sb.toString().getBytes());
	}
	
	private void writeBytesField(String name, byte[] value, OutputStream os)throws IOException{
		os.write(boundary.getBytes());

		StringBuilder sb = new StringBuilder();
		sb.append("Content-Disposition:form-data;Content-Type:application/octet-stream;name=\""+name+"\";filename=\""+name+"\"");
		sb.append("\r\n\r\n\r\n");
		os.write(sb.toString().getBytes());

		os.write(value);

		os.write("\r\n\r\n\r\n".getBytes());
	}
}
