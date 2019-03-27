package com.ethon.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jspsmart.upload.SmartUpload;

public class ValidateParameter {
	private String rule;
	private Object currentObj;
	private HttpServletRequest request;
	private Map<String, String> info = new HashMap<>();
	private SmartUpload smart;

	public ValidateParameter(Object currentObj, HttpServletRequest request, SmartUpload smart, String rule,
			Map<String, String> info) {
		this.currentObj = currentObj;
		this.rule = rule;
		this.request = request;
		this.info = info;
		this.smart = smart;
	}

	public boolean validate() {
		boolean flag = true;
		String res[] = this.rule.split("\\|");
		for (int x = 0; x < res.length; x++) {
			HandleParameter hp = new HandleParameter(currentObj, res[x]);
			String type = hp.handle();
			if(type.contains("[]")) {
				flag = validateMore(type, res[x]);
			} else {
				flag = validateSingle(type, res[x]);
			}
		}
		return flag;
	}

	public boolean validateMore(String type, String res) {
		boolean flag = true;
		String data[] = null;
		if (request.getContentType().contains("multipart/form-data")) {
			data = smart.getRequest().getParameterValues(res);
		} else {
			data = request.getParameterValues(res);
		}
		if ("String[]".equalsIgnoreCase(type)) {
			if (data == null || "".equals(data)) {
				flag = false;
				info.put(res, "内容不能为空");
			}
		} else if ("int[]".equalsIgnoreCase(type)) {
			if(data == null || "".equals(data)) {
				flag = false;
				info.put(res, "内容必须是整数");
			} else {
				for (int y = 0; y < data.length; y++) {
					if (!ValidateUtil.validateRegex(data[y], "\\d+")) {
						flag = false;
						info.put(res, "内容必须是整数");
						break;
					}
				}
			}

		} else if ("integer[]".equalsIgnoreCase(type)) {
			if(data == null || "".equals(data)) {
				flag = false;
				info.put(res, "内容必须是整数");
			} else {
				for (int y = 0; y < data.length; y++) {
					if (!ValidateUtil.validateRegex(data[y], "\\d+")) {
						flag = false;
						info.put(res, "内容必须是整数");
						break;
					}
				}
			}

		} else if ("double[]".equalsIgnoreCase(type)) {
			if(data == null || "".equals(data)) {
				flag = false;
				info.put(res, "内容必须是小数");
			} else {
				for (int z = 0; z < data.length; z++) {
					if (!ValidateUtil.validateRegex(data[z], "\\d+{\\.\\d+}?")) {
						flag = false;
						info.put(res, "内容必须是小数");
						break;
					}
				}
			}
		}
		return flag;
	}

	public boolean validateSingle(String type, String res) {
		boolean flag = true;
		String data = null;
		if (request.getContentType().contains("multipart/form-data")) {
			data = smart.getRequest().getParameter(res);
		} else {
			data = request.getParameter(res);
		}
		if ("String".equalsIgnoreCase(type)) {
			if (!ValidateUtil.validateEmpty(data)) {
				flag = false;
				info.put(res, "内容不能为空");
			}
		} else if ("int".equalsIgnoreCase(type) || "integer".equalsIgnoreCase(type)) {
			if (!ValidateUtil.validateRegex(data, "\\d+")) {
				flag = false;
				info.put(res, "内容必须是整数");
			}
		} else if ("double".equalsIgnoreCase(type)) {
			if (!ValidateUtil.validateRegex(data, "\\d+{\\.\\d+}?")) {
				flag = false;
				info.put(res, "内容必须是小数");
			}
		} else if ("date".equalsIgnoreCase(type)) {
			if (!ValidateUtil.validateRegex(data, "\\d{4}-\\d{2}-\\d{2}")) {
				flag = false;
				info.put(res, "内容必须是日期，2000-10-10");
			}
		}
		return flag;
	}
}
