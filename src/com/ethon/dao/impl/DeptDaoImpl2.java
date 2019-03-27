package com.ethon.dao.impl;

import com.ethon.dao.DeptDao;
import com.ethon.vo.Dept;

public class DeptDaoImpl2 implements DeptDao {

	@Override
	public boolean doCreate(Dept vo) throws Exception {
		System.out.println("===DeptDaoImpl2插入数据===");
		return true;
	}

	@Override
	public boolean findAll() throws Exception {
		System.out.println("===数据列表===");
		return false;
	}

}
