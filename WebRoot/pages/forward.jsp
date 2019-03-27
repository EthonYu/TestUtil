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
	<script type="text/javascript">
		alert("${msg}");
		window.location="<%=basePath%>${url}"
	</script>
</body>
</html>
