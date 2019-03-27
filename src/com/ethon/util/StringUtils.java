package com.ethon.util;

public class StringUtils {
	public static String init(String s) {
		if(s == null | "".equals(s)) {
			return s;
		} else {
			return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
		}
	}
	public static void main(String[] args) {
		System.out.println(init("te"));
	}
}
