package com.ethon.vo;

public class Dept {
	private Integer deptno;
	private String dname;
	private Integer[] loc;
	private Double sal;
	public Double getSal() {
		return sal;
	}
	public void setSal(Double sal) {
		this.sal = sal;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public Integer[] getLoc() {
		return loc;
	}
	public void setLoc(Integer[] loc) {
		this.loc = loc;
	}
	public Integer getDetpno() {
		return deptno;
	}
	public void setDeptno(Integer detpno) {
		this.deptno = detpno;
	}
}
