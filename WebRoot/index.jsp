<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'index.jsp' starting page</title>
  </head>
  
  <body>
  	<form action="dept/insert" method="post" enctype="multipart/form-data">
  		部门编号：	<input type="text" name="dept.deptno" id="dept.deptno"><br>
  		部门名称：	<input type="text" name="dept.dname" id="dept.dname"><br>
  		部门位置：	<input type="checkbox" name="dept.loc" id="dept.loc" value="1">位置1
  				<input type="checkbox" name="dept.loc" id="dept.loc" value="2">位置2
  				<input type="checkbox" name="dept.loc" id="dept.loc" value="3">位置3
  				<input type="checkbox" name="dept.loc" id="dept.loc" value="4">位置4
  				<input type="checkbox" name="dept.loc" id="dept.loc" value="5">位置5<br>
  		选择文件：	<input type="file" name="file1" id="file1"><br>
  				<input type="file" name="file2" id="file2"><br>
  				<input type="file" name="file3" id="file3"><br>
  				<input type="submit" value="提交">
  				<input type="reset" value="重置">
  	</form>
  </body>
</html>
