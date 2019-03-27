package com.ethon.servlet;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.ethon.util.servlet.DispatcherServlet;
import com.ethon.vo.Dept;

@SuppressWarnings("serial")
@WebServlet(name="DeptServlet", urlPatterns="/dept/*")
public class DeptServlet extends DispatcherServlet {
	private final String insertValidate = "dept.deptno|dept.dname|dept.loc";
	private Dept dept = new Dept();
	public String insert() {
		System.out.println("==  Dept-insert ==");
		System.out.println(dept.getDetpno());
		System.out.println(dept.getDname());
		System.out.println(Arrays.toString(dept.getLoc()));
		if(super.isUpload()) {
			Map<Integer, String> fileNames = super.createMultiFileName();
			super.upload(fileNames);
		}
		super.setMsgAndUrl("insert.success.msg", "insert.page");
		return "forward.page";
	}
	public String update() {
		System.out.println("==  Dept-update ==");
		return "";
	}
	@Override
	public String getTitle() {
		return "部门";
	}
	
	public Dept getDept() {
		return dept;
	}
	@Override
	public String getUploadDirectory() {
		return "/upload/dept/";
	}
	@Override
	public String getColumnData() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDefaultColumn() {
		// TODO Auto-generated method stub
		return null;
	}
}
