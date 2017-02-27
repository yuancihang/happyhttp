package com.happyhttp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip压缩解压缩
 *
 */
public class GzipUtil {

	/**
	 * gzip压缩
	 * @param data 原始数据
	 * @return 压缩后的数据
	 * @throws IOException 压缩流会抛出该异常
	 */
	public static byte[] compress(byte[] data) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (GZIPOutputStream gos = new GZIPOutputStream(bos)) {
			gos.write(data);
			gos.finish();
			gos.flush();
		} 
		
		return bos.toByteArray();
	}
	
	/**
	 * gzip解压缩
	 * @param data 原始数据
	 * @return 解压后的数据
	 * @throws IOException 解压流会抛出该异常
	 */
	public static byte[] uncompress(byte[] data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(data))) {
			byte[] buffer = new byte[8192];
			int n = 0;
			while ((n = gis.read(buffer)) >= 0) {
				bos.write(buffer, 0, n);
			}
		} 

		return bos.toByteArray();
	}

}
