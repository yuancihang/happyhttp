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
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] compress(byte[] data) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gos = null;
		try {
			gos = new GZIPOutputStream(bos);
			
			gos.write(data);
			gos.finish();
			gos.flush();
		} finally{
			if(gos != null) gos.close();
		}
		
		return bos.toByteArray();
	}
	
	/**
	 * gzip解压缩
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] uncompress(byte[] data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPInputStream gis = null;
		try {
			gis = new GZIPInputStream(new ByteArrayInputStream(data));

			byte[] buffer = new byte[8192];
			int n = 0;
			while ((n = gis.read(buffer)) >= 0) {
				bos.write(buffer, 0, n);
			}
		} finally {
			if (gis != null)
				gis.close();
		}

		return bos.toByteArray();
	}

}
