package com.ethon.test;

import com.ethon.dao.impl.DeptDaoImpl;

public class DAOFactory {
	//返回代理对象
	public static Object getInstance() {
		return new MyProxy().bind(new DeptDaoImpl());
	}
}
