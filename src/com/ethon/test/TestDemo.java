package com.ethon.test;

import java.util.Arrays;

import com.ethon.util.BeanOperate;
import com.ethon.vo.Emp;

public class TestDemo {
	private Emp emp = new Emp();
	public Emp getEmp() {
		return emp;
	}
	public static void main(String[] args) {
		String attribute = "emp.count";
		String value[] = {"1", "2", "3"};
		TestDemo td = new TestDemo();
		new BeanOperate(td, attribute, value);
		System.out.println(Arrays.toString(td.getEmp().getCount()));
	}
}
