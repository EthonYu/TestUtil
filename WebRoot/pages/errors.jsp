<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">
<title>Errors.jsp</title>
</head>

<body>
	<h1>对不起，程序出现错误，请联系管理员</h1>
	<c:if test="${msg != null}">
		<c:forEach items="${msg}" var="dept">
			<li>${dept.key}= ${dept.value}</li>
		</c:forEach>
	</c:if>
	<h1>
		<a href="<%=basePath%>index.jsp">点击，返回首页！</a>
	</h1>
</body>
</html>
