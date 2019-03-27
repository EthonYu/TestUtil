package com.ethon.test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.ethon.dao.impl.DeptDaoImpl2;

public class MyProxy implements InvocationHandler {
	//首先一定要有真实主题类对象
	private Object obj;
	//通过反射Proxy类的newProxyInstance方法，绑定真实对象返回代理类对象
	public Object bind(Object obj) {
		this.obj = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), 
				obj.getClass().getInterfaces(), this);
	}

	private void prepare() {
		System.out.println("===事务取消===");
	}
	private void commit() {
		System.out.println("===事务提交===");
	}
	private void rollback() {
		System.out.println("===事务回滚===");
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		obj = new DeptDaoImpl2();
		Object reobj = null;
		if(method.getName().contains("do")) {
			this.prepare();
			try {
				reobj = method.invoke(obj, args);
				this.commit();
			} catch(Exception e) {
				this.rollback();
				throw e;
			}
		} else {
			reobj = method.invoke(obj, args);
		}
		return reobj;	
	}

}
