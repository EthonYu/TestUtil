package com.ethon.test;

import com.ethon.dao.DeptDao;
import com.ethon.vo.Dept;

public class Test {
	public static void main(String[] args) throws Exception {
		//此时返回的是代理类对象，但是代理类已经动态实现了真实类的父接口
		DeptDao dao = (DeptDao) DAOFactory.getInstance();
		Dept vo = new Dept();
		dao.doCreate(vo);
	}
}
