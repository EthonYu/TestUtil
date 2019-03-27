package com.ethon.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HandleParameter {
	private Object currentObj;
	private String attribute;
	private String res[];
	
	public HandleParameter(Object currentObj, String attribute) {
		this.currentObj = currentObj;
		this.attribute = attribute;
		this.res = this.attribute.split("\\.");
	}
	
	public String handle() {
		String type = null;
		try {
			for(int x = 0; x < res.length - 1; x++) {
				Method met = this.currentObj.getClass().getMethod("get" + StringUtils.init(res[x]));
				currentObj = met.invoke(this.currentObj);
			}
			Field field = currentObj.getClass().getDeclaredField(res[res.length - 1]);
			type = field.getType().getSimpleName();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return type;
	}
}
