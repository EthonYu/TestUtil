package com.ethon.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class TestUtil {
	public static byte[] getClassFile() {
		byte[] b = null;
		try {
			InputStream is = new FileInputStream(new File("e:/Dept.class"));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] temp = new byte[1024];
			int len = 0;
			while((len = is.read(temp)) != -1) {
				bos.write(temp, 0, len);
			}
			b = bos.toByteArray();
			System.out.println(b);
			is.close();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
