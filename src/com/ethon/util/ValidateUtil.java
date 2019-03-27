package com.ethon.util;

public class ValidateUtil {
	public static boolean validateEmpty(String s) {
		if(s == null || "".equals(s)) {
			return false;
		}
		return true;
	}
	
	public static boolean validateRegex(String s, String regex) {
		if(validateEmpty(s)) {
			return s.matches(regex);
		}
		return false;
	}
	
	public static boolean validateSame(String s1, String s2) {
		if(s1.equals(s2)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String s = "123";
		System.out.println(s.matches("\\d+"));
	}
}
