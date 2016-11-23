package com.happyhttp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class StreamUtil {

	public static final int EOF = -1;
	
	public static void output(InputStream in, OutputStream out, boolean closeIn, boolean closeOut) throws IOException{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			bis = new BufferedInputStream(in);
			bos = new BufferedOutputStream(out);
			
			int data = 0;
			while ((data = bis.read()) != EOF) {
				bos.write(data);
			}
			bos.flush();
		} finally {
			try {
				if(closeIn && (bis != null)) bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(closeOut && (bos != null)) bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void output(Reader reader, Writer writer, boolean closeReader, boolean closeWriter) throws IOException{
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try{
			br = new BufferedReader(reader);
			bw = new BufferedWriter(writer);
			
			int data = 0;
			while ((data = br.read()) != EOF) {
				bw.write(data);
			}
			bw.flush();
		}finally{
			try {
				if(closeReader && (br != null)) br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(closeWriter && (bw != null)) bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String readStreamAsText(InputStream in, boolean closeIn) throws UnsupportedEncodingException, IOException{
		StringWriter sw = new StringWriter();
		
		output(new InputStreamReader(in, "UTF-8"), sw, closeIn, true);
		
		return sw.toString();
	}
	
	public static void readStreamAsFile(InputStream in, File file, boolean closeIn) throws FileNotFoundException, IOException{
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		output(in, new FileOutputStream(file), closeIn, true);
	}
}

