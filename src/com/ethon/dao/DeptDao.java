package com.ethon.dao;

import com.ethon.vo.Dept;

public interface DeptDao {
	public boolean doCreate(Dept vo) throws Exception;
	public boolean findAll() throws Exception;
}
