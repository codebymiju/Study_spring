<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<!-- inc/top.jsp 페이지 삽입 -->
		<jsp:include page="inc/top.jsp"></jsp:include>
	</header>
	<article>
		<h1>redirect.jsp</h1>
		<h3>name 파라미터값 : ${param.name }</h3>
		<h3>age 파라미터값 : ${param.age }</h3>
	</article>
</body>
</html>









