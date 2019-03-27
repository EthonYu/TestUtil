package com.ethon.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanOperate {
	private Object currentObj;
	private String attribute;
	private String value;
	private String[] arrayValue;
	private String res[];

	/**
	 * 提供属性和数据，直接修改Java简单类的属性
	 * 
	 * @param currentObj
	 *            操作对象
	 * @param attribute
	 *            操作的属性
	 * @param value
	 *            传入的数据
	 */
	public BeanOperate(Object currentObj, String attribute, String value) {
		this.currentObj = currentObj;
		this.attribute = attribute;
		this.value = value;
		this.res = this.attribute.split("\\.");
		this.handle();
		this.setValue();
	}

	/**
	 * 针对于要修改的Java简单类的属性是数组
	 * 
	 * @param currentObj
	 *            操作对象
	 * @param attribute
	 *            操作的属性
	 * @param arrayValue
	 *            传入的数据
	 */
	public BeanOperate(Object currentObj, String attribute, String[] arrayValue) {
		this.currentObj = currentObj;
		this.attribute = attribute;
		this.arrayValue = arrayValue;
		this.res = this.attribute.split("\\.");
		this.handle();
		this.setValueArray();
	}

	private void handle() {
		try {
			for (int x = 0; x < res.length - 1; x++) {
				Method met = this.currentObj.getClass().getMethod("get" + StringUtils.init(res[x]));
				currentObj = met.invoke(this.currentObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setValue() {
		if (value == null || "".equals(value)) {
			System.out.println("value数值为空");
		} else {
			try {
				Field field = currentObj.getClass().getDeclaredField(res[res.length - 1]);
				Method met = currentObj.getClass().getMethod("set" + StringUtils.init(res[res.length - 1]),
						field.getType());
				String type = field.getType().getSimpleName();
				if (type.equalsIgnoreCase("int") | type.equalsIgnoreCase("Integer")) {
					met.invoke(currentObj, Integer.parseInt(value));
				} else if (type.equalsIgnoreCase("double")) {
					met.invoke(currentObj, Double.parseDouble(value));
				} else if (type.equalsIgnoreCase("string")) {
					met.invoke(currentObj, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setValueArray() {
		if (arrayValue == null) {
			System.out.println("arrayValue数值为空");
		} else {
			try {
				Field field = currentObj.getClass().getDeclaredField(res[res.length - 1]);
				Method met = currentObj.getClass().getMethod("set" + StringUtils.init(res[res.length - 1]),
						field.getType());
				String type = field.getType().getSimpleName();
				if (type.equalsIgnoreCase("int[]")) {
					int temp[] = new int[arrayValue.length];
					for (int x = 0; x < temp.length; x++) {
						temp[x] = Integer.parseInt(arrayValue[x]);
					}
					met.invoke(currentObj, new Object[] { temp });
				} else if (type.equalsIgnoreCase("integer[]")) {
					Integer temp[] = new Integer[arrayValue.length];
					for (int x = 0; x < temp.length; x++) {
						temp[x] = Integer.parseInt(arrayValue[x]);
					}
					met.invoke(currentObj, new Object[] { temp });
				} else if (type.equalsIgnoreCase("double[]")) {
					Double temp[] = new Double[arrayValue.length];
					for (int x = 0; x < temp.length; x++) {
						temp[x] = Double.parseDouble(arrayValue[x]);
					}
					met.invoke(currentObj, new Object[] { temp });
				} else if (type.equalsIgnoreCase("String[]")) {
					met.invoke(currentObj, new Object[] { arrayValue });
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
