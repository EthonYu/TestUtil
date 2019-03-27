package com.ethon.util.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ethon.util.BeanOperate;
import com.ethon.util.HandleParameter;
import com.ethon.util.ValidateParameter;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

@SuppressWarnings("serial")
public abstract class DispatcherServlet extends HttpServlet {
	private static final String PAGES_BASENAME = "Pages";
	private static final String MESSAGES_BASENAME = "Messages";
	// 在这里定义这两个对象，是为了在这个类中的所有方法里都可以使用这两个类对象
	private ResourceBundle pagesResource;
	private ResourceBundle messagesResource;
	private SmartUpload smart = null;
	// 分页需要使用的数据
	private Integer currentPage = 1;
	private Integer lineSize = 5;
	private String keyWord;
	private String column;
	// 定义这两个类对象以供子类使用
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	// 初始化的时候，获得这两个类对象具体的实现
	public void init() throws ServletException {
		this.pagesResource = ResourceBundle.getBundle(PAGES_BASENAME);
		this.messagesResource = ResourceBundle.getBundle(MESSAGES_BASENAME);
	}

	/**
	 * 获得要返回的路径
	 * 
	 * @param key
	 *            properties中使用的key值
	 * @return 存在key值返回路径，不存在返回null
	 */
	public String getPath(String key) {
		return pagesResource.getString(key);
	}

	/**
	 * 获得提示信息
	 * 
	 * @param key
	 *            properties中使用的key值
	 * @param args
	 *            因为message中有占位符，所以传入这个参数
	 * @return 存在key值返回message，不存在返回null
	 */
	public String getMsg(String key, String... args) {
		String s = messagesResource.getString(key);
		// 如果没有传入args参数的话，利用抽象方法在子类中自己设置
		if (args.length > 0) {
			return MessageFormat.format(s, args);
		} else {
			return MessageFormat.format(s, this.getTitle());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		request.setCharacterEncoding("UTF-8");
		String path = "errors.page";
		String status = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
		// this就是要使用调用的对象,status就是要使用的方法
		if (status != null && status.length() > 0) {
			if (request.getContentType() != null) {
				if (request.getContentType().contains("multipart/form-data")) {
					this.smart = new SmartUpload();
					try {
						smart.initialize(this.getServletConfig(), request, response);
						smart.upload();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (this.validateRequest(status)) {
			this.setValue();
			try {
				Method method = this.getClass().getMethod(status);
				path = (String) method.invoke(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher(this.getPath(path)).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void setValue() {
		if (request.getContentType() != null) {
			if (request.getContentType().contains("multipart/form-data")) {
				// 取得所有的请求的名称
				Enumeration<String> enu = smart.getRequest().getParameterNames();
				while (enu.hasMoreElements()) {
					String parameterName = enu.nextElement();
					// 获得所有参数的类型，类型不同处理的方式不同，数据还是单个数据
					HandleParameter hp = new HandleParameter(this, parameterName);
					String test = hp.handle();
					if (test != null) {
						if (test.contains("[]")) {
							String value[] = smart.getRequest().getParameterValues(parameterName);
							new BeanOperate(this, parameterName, value);
						} else {
							String value = smart.getRequest().getParameter(parameterName);
							new BeanOperate(this, parameterName, value);
						}
					}
				}
			}
		} else {
			// 取得所有的请求的名称
			Enumeration<String> enu = request.getParameterNames();
			while (enu.hasMoreElements()) {
				String parameterName = enu.nextElement();
				// 获得所有参数的类型，类型不同处理的方式不同，数据还是单个数据
				HandleParameter hp = new HandleParameter(this, parameterName);
				String test = hp.handle();
				if (test != null) {
					if (test.contains("[]")) {
						String value[] = request.getParameterValues(parameterName);
						new BeanOperate(this, parameterName, value);
					} else {
						String value = request.getParameter(parameterName);
						new BeanOperate(this, parameterName, value);
					}
				}
			}
		}
	}

	/**
	 * 验证接收到的数据是否有效
	 * 
	 * @return 有效返回true，无效返回false
	 */
	private boolean validateRequest(String status) {
		boolean flag = false;
		String rule = null;
		try {
			Field field = this.getClass().getDeclaredField(status + "Validate");
			field.setAccessible(true);
			rule = (String) field.get(this);
		} catch (Exception e) { // 表示数据不存在
			flag = true;
		}
		Map<String, String> info = new HashMap<>();
		ValidateParameter vp = new ValidateParameter(this, request, smart, rule, info);
		flag = vp.validate();
		request.setAttribute("msg", info);
		return flag;
	}

	public void setMsgAndUrl(String msgKey, String urlKey) {
		// System.out.println(this.getMsg(msgKey, this.getTitle()));
		// System.out.println(this.getPath(urlKey));
		this.request.setAttribute("msg", this.getMsg(msgKey));
		this.request.setAttribute("url", this.getPath(urlKey));
	}

	/**
	 * 分页处理
	 */
	public void handleSplit() {
		try {
			this.currentPage = Integer.parseInt(this.request.getParameter("cp"));
		} catch (Exception e) {

		}
		try {
			this.lineSize = Integer.parseInt(this.request.getParameter("ls"));
		} catch (Exception e) {

		}
		this.column = this.request.getParameter("col");
		this.keyWord = this.request.getParameter("kw");
		if (this.column == null) {
			this.column = this.getDefaultColumn();
		}
		if (this.keyWord == null) {
			this.keyWord = ""; // 表示查询全部
		}
		this.request.setAttribute("currentPage", this.currentPage);
		this.request.setAttribute("lineSize", this.lineSize);
		this.request.setAttribute("column", this.column);
		this.request.setAttribute("keyWord", this.keyWord);
		this.request.setAttribute("columnData", this.getColumnData());
	}

	public Integer getCurrentPage() {
		return this.currentPage;
	}

	public Integer getLineSize() {
		return this.lineSize;
	}

	public String getColumn() {
		return this.column;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	/**
	 * 判断是否有文件上传
	 * 
	 * @return 有文件上传返回true，没有文件上传返回false
	 */
	public boolean isUpload() {
		try {
			return this.smart.getFiles().getSize() > 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 返回上传文件的个数
	 * 
	 * @return
	 */
	public int getUploadCount() {
		return this.smart.getFiles().getCount();
	}

	/**
	 * 创建一个新的文件名称
	 * 
	 * @return
	 */
	public String createSingleFileName() {
		String fileName = null;
		if (this.isUpload()) {
			fileName = UUID.randomUUID() + "." + this.smart.getFiles().getFile(0).getFileExt();
		}
		return fileName;
	}

	/**
	 * 取得一组上传的文件的名字，放入到Map集合中
	 * 
	 * @return
	 */
	public Map<Integer, String> createMultiFileName() {
		Map<Integer, String> all = new HashMap<>();
		String fileName = null;
		if (this.isUpload()) {
			for (int x = 0; x < this.getUploadCount(); x++) {
				if (this.smart.getFiles().getFile(x).getSize() > 0) {
					fileName = UUID.randomUUID() + "." + this.smart.getFiles().getFile(x).getFileExt();
					all.put(x, fileName);
				}
			}
		}
		return all;
	}

	/**
	 * 设置保存文件
	 * 
	 * @param index
	 *            smart上传文件的索引
	 * @param fileName
	 *            文件的名称
	 */
	private void saveFile(int index, String fileName) {
		String filePath = super.getServletContext().getRealPath(this.getUploadDirectory()) + fileName;
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			this.smart.getFiles().getFile(index).saveAs(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存单个文件
	 * 
	 * @param fileName
	 *            上传文件的名字
	 */
	public void upload(String fileName) {
		this.saveFile(0, fileName);
	}

	/**
	 * 保存多个文件
	 * 
	 * @param all
	 */
	public void upload(Map<Integer, String> all) {
		Set<Entry<Integer, String>> fileName = all.entrySet();
		Iterator<Entry<Integer, String>> it = fileName.iterator();
		while (it.hasNext()) {
			Entry<Integer, String> entry = it.next();
			this.saveFile(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 */
	public void deleteFile(String fileName) {
		String filePath = this.getServletContext().getRealPath(this.getUploadDirectory()) + fileName;
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 批量删除文件
	 * 
	 * @param fileName
	 *            所有要删除的文件名
	 */
	public void deleteFile(Set<String> fileName) {
		Iterator<String> it = fileName.iterator();
		while (it.hasNext()) {
			this.deleteFile(it.next());
		}
	}

	public SmartUpload getSmart() {
		return smart;
	}

	public abstract String getTitle();

	public abstract String getUploadDirectory();

	/**
	 * 取得要分页搜索条的数据列
	 * 
	 * @return
	 */
	public abstract String getColumnData();

	/**
	 * 得到默认查询的数据列
	 * 
	 * @return
	 */
	public abstract String getDefaultColumn();
}
