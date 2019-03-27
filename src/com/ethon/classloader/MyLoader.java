package com.ethon.classloader;

import com.ethon.test.TestUtil;

public class MyLoader extends ClassLoader{
	public Class<?> getMyClass(String className) throws ClassNotFoundException {
		byte[] b = TestUtil.getClassFile();
		return super.defineClass(className, b, 0, b.length);
	}
	
}
