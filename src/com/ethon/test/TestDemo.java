package com.ethon.test;

import java.util.Arrays;

import com.ethon.annotation.MyFlag;
import com.ethon.vo.Emp;
@MyFlag
class TestAnno {
	public void test() {
		
	}
}
public class TestDemo {
	private Emp emp = new Emp();
	public Emp getEmp() {
		return emp;
	}
	public static void main(String[] args) {
		TestAnno ta = new TestAnno();
		System.out.println(Arrays.toString(ta.getClass().getAnnotations()));
		MyFlag mf = ta.getClass().getAnnotation(MyFlag.class);
		System.out.println(mf.name());
		System.out.println(mf.value());
	}
}
